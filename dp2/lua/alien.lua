---@class alien
---@field funcptr fun(ptr:userdata):alien.func
local _M = {}
local core = require("alien_c")

local type = type
local math = math
local error = error
local pcall = pcall
local table = table
local ipairs = ipairs
local rawset = rawset
local setmetatable = setmetatable

local array_methods = {}

local function array_next(arr, i)
  if i < arr.length then
    return i + 1, arr[i + 1]
  else
    return nil
  end
end

function array_methods:ipairs()
  return array_next, self, 0
end

function array_methods:realloc(newlen)
  self.buffer:realloc(newlen * self.size)
  self.length = newlen
end

local function array_get(arr, key)
  if type(key) == "number" then
    if key < 1 or key > arr.length then
      error("array access out of bounds")
    end
    local offset = (key - 1) * arr.size + 1
    return arr.buffer:get(offset, arr.type)
  else
    return array_methods[key]
  end
end

local function array_set(arr, key, val)
  if type(key) == "number" then
    if key < 1 or key > arr.length then
      error("array access out of bounds")
    end
    local offset = (key - 1) * arr.size + 1
    arr.buffer:set(offset, val, arr.type)
    if arr.type == "pointer" then
      arr.pinned[key] = val
    end
  else
    rawset(arr, key, val)
  end
end

local function struct_new(s_proto, ptr)
  local buf = core.buffer(ptr or s_proto.size)
  local function struct_get(_, key)
    if s_proto.offsets[key] then
      return buf:get(s_proto.offsets[key] + 1, s_proto.types[key])
    else
      error("field " .. key .. " does not exist")
    end
  end
  local function struct_set(_, key, val)
    if s_proto.offsets[key] then
      buf:set(s_proto.offsets[key] + 1, val, s_proto.types[key])
    else
      error("field " .. key .. " does not exist")
    end
  end
  return setmetatable({}, { __index = struct_get, __newindex = struct_set,
                            __call = function () return buf end })
end

local function struct_byval(s_proto)
  local types = {}
  local size = s_proto.size
  for i = 0, size - 1, 4 do
    if size - i == 1 then
      types[#types + 1] = "char"
    elseif size - i == 2 then
      types[#types + 1] = "short"
    else
      types[#types + 1] = "int"
    end
  end
  return table.unpack(types)
end

function _M.array(t, length, init)
  local ok, size = pcall(core.sizeof, t)
  if not ok then
    error("type " .. t .. " does not exist")
  end
  if type(length) == "table" then
    init = length
    length = #length
  end
  local arr = { type = t, length = length, size = size, pinned = {} }
  -- FIXME: When Lua 5.1 support is dropped, add a __len metamethod
  setmetatable(arr, { __index = array_get, __newindex = array_set })
  if type(init) == "userdata" then
    arr.buffer = init
  else
    arr.buffer = core.buffer(size * length)
    if type(init) == "table" then
      for i = 1, length do
        if type(init[i]) == "string" then
          local offset = (i - 1) * size + 1
          arr.pinned[i] = core.buffer(#init[i] + 1)
          arr.buffer:set(offset, arr.pinned[i], "pointer")
        end
        arr[i] = init[i]
      end
    end
  end
  return arr
end

function _M.byval(buf)
  if buf.size then
    local size = buf.size
    -- local types = { "char", "short"}
    local vals = {}
    for i = 1, size, 4 do
      if size - i == 0 then
        vals[#vals + 1] = buf:get(i, "char")
      elseif size - i == 1 then
        vals[#vals + 1] = buf:get(i, "short")
      else
        vals[#vals + 1] = buf:get(i, "int")
      end
    end
    return table.unpack(vals)
  else
    error("this type of buffer can't be passed by value")
  end
end

function _M.defstruct(t)
  local off = 0
  local names, offsets, types = {}, {}, {}
  for _, field in ipairs(t) do
    local name, type1 = field[1], field[2]
    names[#names + 1] = name
    off = math.ceil(off / core.align(type1)) * core.align(type1)
    offsets[name] = off
    types[name] = type1
    off = off + core.sizeof(type1)
  end
  return { names = names, offsets = offsets, types = types, size = off, new = struct_new, byval = struct_byval }
end

function _M.func(f, ...)
  local cb = core.callback(f)
  cb:types(...)
  return cb
end

function _M.callback(f, ...)
  return _M.func(f, ...)
end

setmetatable(_M, { __index = core })

---@class alien.func
local alien_func = {}
---@param retType string
---@return void
function alien_func:types(retType, ...) end

return _M
