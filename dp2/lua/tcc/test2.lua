local tcc = require("tcc")
local alien = require("alien")

local state = tcc.new()

state:set_error_func(function(str)
    print(("tcc error\n%s"):format(str))
end)

local function add(a, b)
    return a + b
end

local my_program = [[
#include <stdio.h>

extern int add(int, int);

int fib(int n)
{
  if (n <= 2)
    return 1;
  else
    return fib(n-1) + fib(n-2);
}

int foo(int n)
{
  printf("Hello World!\n");
  printf("fib(%d) = %d\n", n, fib(n));
  printf("add(%d, %d) = %d\n", n, 2 * n, add(n, 2 * n));
  return 0;
}
]]

assert(state:compile_string(my_program))
state:add_symbol("add", alien.func(add, "int", "int", "int"))
assert(state:relocate())
local foo = state:get_func_symbol("foo")
foo:types("int", "int")
foo(32)

print("########### lua-tcc test all done! ###########")
