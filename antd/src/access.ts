/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: API.CurrentUser } | undefined) {
  const { currentUser } = initialState ?? {};
  return {
    isAdmin: currentUser && currentUser.isAdmin,// 只有管理员可访问
    hashRole: (role:string) => currentUser && currentUser.role && currentUser.role.includes(role), // 包含指定角色才有权限访问
    hashPre: (pre:string) => currentUser && currentUser.resource && currentUser.resource.includes(pre), // 包含指定资源才有权限访问
  };
}
