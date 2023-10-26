local tcc = require("tcc")
local alien = require("alien")

local state = tcc.new()

state:set_error_func(function(str)
    print(("tcc error\n%s"):format(str))
end)

local output = ""

local test = function(msg)
    output = msg
end

assert(state:compile_string([[
  #include <stdlib.h>
  #include <string.h>
  #include <stdio.h>

  extern void test(char* msg);

  int main(int argc, char** argv)
  {
    if (argc == 0) { return 1; }
    char* msg = (char*)malloc(strlen(argv[0]) + 8);
    sprintf(msg, "Hello, %s!\n", argv[0]);
    test(msg);
    free(msg);
    return 0;
  }
]]))

state:add_symbol("test", alien.func(test, "void", "string"))

assert(state:run("World")== 0)
assert(output == "Hello, World!\n")



print("########### lua-tcc test all done! ###########")
