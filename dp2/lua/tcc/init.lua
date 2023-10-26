local _M = {}
local alien = require("alien")

local type = type
local table = table
local assert = assert
local select = select
local setmetatable = setmetatable

local home = "/dp2/lua/tcc"
local sofile = home .. "/lib/libtcc.so"
local libtcc = alien.load(sofile)

assert(libtcc)

-- LIBTCCAPI TCCState *tcc_new(void);
libtcc.tcc_new:types("pointer")
-- LIBTCCAPI void tcc_delete(TCCState *s);
libtcc.tcc_delete:types("void", "pointer")
-- LIBTCCAPI void tcc_set_lib_path(TCCState *s, const char *path);
libtcc.tcc_set_lib_path:types("void", "pointer", "string")
-- LIBTCCAPI void tcc_set_error_func(TCCState *s, void *error_opaque, void (*error_func)(void *opaque, const char *msg));
libtcc.tcc_set_error_func:types("void", "pointer", "pointer", "callback")
-- LIBTCCAPI void tcc_set_options(TCCState *s, const char *str);
libtcc.tcc_set_options:types("void", "pointer", "string")

-- preprocessor

-- LIBTCCAPI int tcc_add_include_path(TCCState *s, const char *pathname);
libtcc.tcc_add_include_path:types("int", "pointer", "string")
-- LIBTCCAPI int tcc_add_sysinclude_path(TCCState *s, const char *pathname);
libtcc.tcc_add_sysinclude_path:types("int", "pointer", "string")
-- LIBTCCAPI void tcc_define_symbol(TCCState *s, const char *sym, const char *value);
libtcc.tcc_define_symbol:types("void", "pointer", "string", "callback")
-- LIBTCCAPI void tcc_undefine_symbol(TCCState *s, const char *sym);
libtcc.tcc_undefine_symbol:types("void", "pointer", "string")

-- compiling

-- LIBTCCAPI int tcc_add_file(TCCState *s, const char *filename);
libtcc.tcc_add_file:types("int", "pointer", "string")
-- LIBTCCAPI int tcc_compile_string(TCCState *s, const char *buf);
libtcc.tcc_compile_string:types("int", "pointer", "string")

-- linking commands

local OUTPUT = {
    MEM = 1,
    EXE = 2,
    DLL = 3,
    OBJ = 4,
}

-- LIBTCCAPI int tcc_set_output_type(TCCState *s, int output_type);
libtcc.tcc_set_output_type:types("int", "pointer", "int")
-- LIBTCCAPI int tcc_add_library_path(TCCState *s, const char *pathname);
libtcc.tcc_add_library_path:types("int", "pointer", "string")
-- LIBTCCAPI int tcc_add_library(TCCState *s, const char *libraryname);
libtcc.tcc_add_library:types("int", "pointer", "string")
-- LIBTCCAPI int tcc_add_symbol(TCCState *s, const char *name, const void *val);
libtcc.tcc_add_symbol:types("int", "pointer", "string", "pointer")
-- LIBTCCAPI int tcc_output_file(TCCState *s, const char *filename);
libtcc.tcc_output_file:types("int", "pointer", "string")
-- LIBTCCAPI int tcc_run(TCCState *s, int argc, char **argv);
libtcc.tcc_run:types("int", "pointer", "int", "pointer")
-- LIBTCCAPI int tcc_relocate(TCCState *s1, void *ptr);
libtcc.tcc_relocate:types("int", "pointer", "ptrdiff_t")
-- LIBTCCAPI void *tcc_get_symbol(TCCState *s, const char *name);
libtcc.tcc_get_symbol:types("pointer", "pointer", "string")

local mt = {}

function mt:set_home_path(path)
    libtcc.tcc_set_lib_path(self.s, path .. "/lib/tcc")
    libtcc.tcc_add_sysinclude_path(self.s, path .. "/lib/tcc/include")
end

function mt:set_error_func(func)
    local wrapper = function(_, str)
        return func(str)
    end
    local cb = alien.callback(wrapper, "void", "pointer", "string")
    libtcc.tcc_set_error_func(self.s, nil, cb)
end

function mt:set_options(str)
    libtcc.tcc_set_options(self.s, str)
end

function mt:add_include_path(path)
    return libtcc.tcc_add_include_path(self.s, path) == 0
end

function mt:add_sysinclude_path(path)
    return libtcc.tcc_add_sysinclude_path(self.s, path) == 0
end

function mt:define_symbol(sym, value)
    libtcc.tcc_define_symbol(self.s, sym, value)
end

function mt:undefine_symbol(sym)
    libtcc.tcc_undefine_symbol(self.s, sym)
end

function mt:add_file(filename)
    return libtcc.tcc_add_file(self.s, filename) == 0
end

function mt:compile_string(str)
    return libtcc.tcc_compile_string(self.s, str) == 0
end

function mt:set_output_type(out_type)
    return libtcc.tcc_set_output_type(self.s, out_type) == 0
end

function mt:add_library_path(pathname)
    return libtcc.tcc_add_library_path(self.s, pathname) == 0
end

function mt:add_library(libname)
    return libtcc.tcc_add_library(self.s, libname) == 0
end

function mt:add_symbol(sym, value)
    return libtcc.tcc_add_symbol(self.s, sym, value) == 0
end

function mt:output_file(filename)
    return libtcc.tcc_output_file(self.s, filename) == 0
end

function mt:run(...)
    local pt = {}
    local argc = select("#", ...)

    for i = 1, argc do
        local k = ("p%d"):format(i)

        table.insert(pt, { k, "pointer"})
    end

    local argv = alien.defstruct(pt):new()

    for i = 1, argc do
        local k = ("p%d"):format(i)
        local arg = select(i, ...)

        assert(type(arg) == "string")
        argv[k] = alien.buffer(arg)
    end

    return libtcc.tcc_run(self.s, argc, argv())
end

function mt:relocate()
    -- AUTO
    return libtcc.tcc_relocate(self.s, 1) == 0
end

function mt:get_var_symbol(sym)
    return libtcc.tcc_get_symbol(self.s, sym)
end

function mt:get_func_symbol(sym)
    local value = libtcc.tcc_get_symbol(self.s, sym)
    if not value then
        return nil
    end

    return alien.funcptr(value)
end

_M.new = function(nosysenv)
    local t = {}
    t.s = libtcc.tcc_new()
    if not t.s then
        return nil
    end

    setmetatable(t, { __index = mt, __gc = function(self) libtcc.tcc_delete(self.s) end })

    t:set_home_path(home)
    t:set_output_type(OUTPUT.MEM)

    if not nosysenv then
        t:add_library_path("/usr/local/lib")
        t:add_library_path("/usr/lib")
        t:add_library_path("/lib")

        t:add_include_path("/usr/local/include")
        t:add_include_path("/usr/include")

    end

    return t
end

_M.OUTPUT = OUTPUT

return _M
