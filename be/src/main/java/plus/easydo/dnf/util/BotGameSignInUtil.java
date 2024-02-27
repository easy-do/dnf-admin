package plus.easydo.dnf.util;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import plus.easydo.dnf.dto.SignInConfigDate;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.entity.DaBotPlatformBind;
import plus.easydo.dnf.entity.DaSignInConf;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.DaBotPlatformBindManager;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.service.SignInService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 * @description 机器人签到工具类
 * @date 2024/2/24
 */

public class BotGameSignInUtil {

    private BotGameSignInUtil() {

    }

    private static DaBotPlatformBindManager daBotPlatformBindManager;

    private static GameRoleService gameRoleService;

    private static SignInService signInService;


    private static DaBotPlatformBindManager getDaBotPlatformBindManager(){
        if(Objects.isNull(daBotPlatformBindManager)){
            return SpringUtil.getBean(DaBotPlatformBindManager.class);
        }
        return daBotPlatformBindManager;
    }

    private static GameRoleService getGameRoleService(){
        if(Objects.isNull(gameRoleService)){
            return SpringUtil.getBean(GameRoleService.class);
        }
        return gameRoleService;
    }

    private static SignInService getSignInService(){
        if(Objects.isNull(signInService)){
            return SpringUtil.getBean(SignInService.class);
        }
        return signInService;
    }

    /**
     * 是否绑定账号
     *
     * @param userId userId
     * @return boolean
     * @author laoyu
     * @date 2024/2/24
     */
    public static boolean isBindAccount(String userId){
        return getDaBotPlatformBindManager().isBindAccount(userId);
    }

    /**
     * 是否绑定角色
     *
     * @param userId userId
     * @return boolean
     * @author laoyu
     * @date 2024/2/24
     */
    public static boolean isBindGameRole(String userId){
        return getDaBotPlatformBindManager().isBindRole(userId);
    }



    /**
     * 绑定账号
     *
     * @param userId userId
     * @param accounts accounts
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/24
     */
    public static String bindAccount(String userId,String accounts){
        if(isBindAccount(userId)){
            return "已绑定账号";
        }
        Accounts account = BotAccountUtils.getAccountsService().getByUserName(accounts);
        if(Objects.isNull(account)){
            return "绑定账号不存在";
        }
        if(getDaBotPlatformBindManager().isBindAccounts(account.getUid())){
            return "目标账号已绑定";
        }
        boolean res = getDaBotPlatformBindManager().bind(userId,account.getUid());
        return res? "绑定成功":"绑定失败";
    }

    /**
     * 获取绑定的账号
     *
     * @param userId userId
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-26
     */
    public static DaBotPlatformBind getBindInfo(String userId){
        return getDaBotPlatformBindManager().getBindByPlatformNumber(userId);
    }

    /**
     * 获取角色列表
     *
     * @param userId userId
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/24
     */
    public static String getGameRole(String userId){
        DaBotPlatformBind daBotPlatformBind = getDaBotPlatformBindManager().getBindByPlatformNumber(userId);
        if(Objects.isNull(daBotPlatformBind)){
            return "未找到角色,原因:未绑定账号";
        }
        List<CharacInfo> roleList = getGameRoleService().roleList(daBotPlatformBind.getUid(), null);
        StringBuilder stringBuilder = new StringBuilder();
        roleList.forEach(characInfo -> stringBuilder.append("编号.").append(characInfo.getCharacNo()).append(" 角色名:").append(characInfo.getCharacName()).append(" "));
        return "找到以下角色：" + stringBuilder;
    }

    /**
     * 获取角色列表
     *
     * @param userId userId
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/24
     */
    public static List<CharacInfo> getGameRoleList(String userId){
        DaBotPlatformBind daBotPlatformBind = getDaBotPlatformBindManager().getBindByPlatformNumber(userId);
        if(Objects.isNull(daBotPlatformBind)){
            return Collections.emptyList();
        }
        return getGameRoleService().roleList(daBotPlatformBind.getUid(), null);
    }

    /**
     * 绑定角色
     *
     * @param userId userId
     * @param roleId roleId
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/24
     */
    public static String bindGameRole(String userId,String roleId){
        if(!BotScriptUtils.isLong(roleId)){
            return "绑定失败,原因:错误的角色编号格式";
        }
        DaBotPlatformBind daBotPlatformBind = getDaBotPlatformBindManager().getBindByPlatformNumber(userId);
        if(Objects.isNull(daBotPlatformBind)){
            return "绑定失败,原因:未绑定账号";
        }
        if(Objects.nonNull(daBotPlatformBind.getRoleId())){
            return "绑定失败,原因:已绑定角色";
        }
        long longRoleId = Long.parseLong(roleId);
        List<CharacInfo> characInfos = getGameRoleList(userId);
        for (CharacInfo characInfo : characInfos){
            if(characInfo.getCharacNo().equals(longRoleId)){
                daBotPlatformBind.setRoleId(Long.parseLong(roleId));
                boolean res = getDaBotPlatformBindManager().updateBind(daBotPlatformBind);
                return res? "绑定成功":"绑定失败";
            }
        }
        return "绑定失败,原因:角色【"+longRoleId+"】不在绑定账号内";
    }


    /**
     * 机器人签到
     *
     * @param userId 平台用户编号
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/24
     */
    public static String botSingIn(String userId){
        DaBotPlatformBind daBotPlatformBind = getDaBotPlatformBindManager().getBindByPlatformNumber(userId);
        if(Objects.isNull(daBotPlatformBind)){
            return "签到失败,原因:未绑定账号";
        }
        if(Objects.isNull(daBotPlatformBind.getRoleId())){
            return "签到失败,原因:未绑定角色";
        }
        try {
            getSignInService().botCharacSign(daBotPlatformBind.getRoleId(),daBotPlatformBind.getUid());
        }catch (BaseException e){
            return "签到失败,原因:"+ e.getMessage();
        }
        DaSignInConf signInConf = getSignInService().getTodaySignConf();
        String configJsonStr = signInConf.getConfigJson();
        List<SignInConfigDate> configData = JSONUtil.toBean(configJsonStr, new TypeReference<>() {
        }, true);
        StringBuilder stringBuilder = new StringBuilder();
        for (SignInConfigDate conf:configData){
            stringBuilder.append(conf.getName()).append(conf.getQuantity()).append("个").append(" ");
        }
        return "签到成功,获得以下奖励:"+stringBuilder;
    }

}
