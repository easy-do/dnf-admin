declare namespace API {
  type AccountCargo = {
    money?: number;
    capacity?: number;
    cargo?: string[];
    occTime?: string;
    mid?: string;
  };

  type AccountCargoDto = {
    money?: number;
    capacity?: number;
    mid?: string;
  };

  type Accounts = {
    uid?: string;
    accountname?: string;
    password?: string;
    qq?: string;
    vip?: string;
    admin?: boolean;
  };

  type AccountsQo = {
    current?: number;
    pageSize?: number;
    uid?: string;
    accountname?: string;
    qq?: string;
    vip?: string;
  };

  type allListParams = {
    name?: string;
  };

  type AuthRoleResourceDto = {
    roleId: string;
    resourceIds: string[];
  };

  type CaptchaVo = {
    key?: string;
    img?: string;
  };

  type ChannelQo = {
    current?: number;
    pageSize?: number;
    pid?: string;
    channelName?: string;
    channelStatus?: boolean;
    fridaStatus?: boolean;
  };

  type CharacInfo = {
    characNo?: string;
    characName?: string;
    village?: number;
    job?: number;
    lev?: number;
    exp?: number;
    growType?: number;
    hp?: number;
    maxHP?: number;
    maxMP?: number;
    phyAttack?: number;
    phyDefense?: number;
    magAttack?: number;
    magDefense?: number;
    elementResist?: string[];
    specProperty?: string[];
    invenWeight?: number;
    hpRegen?: number;
    mpRegen?: number;
    moveSpeed?: number;
    attackSpeed?: number;
    castSpeed?: number;
    hitRecovery?: number;
    jump?: number;
    characWeight?: number;
    fatigue?: number;
    maxFatigue?: number;
    premiumFatigue?: number;
    maxPremiumFatigue?: number;
    createTime?: string;
    lastPlayTime?: string;
    dungeonClearPoint?: number;
    deleteTime?: string;
    deleteFlag?: number;
    guildId?: string;
    guildRight?: number;
    memberFlag?: number;
    sex?: number;
    expertJob?: number;
    skillTreeIndex?: number;
    linkCharacNo?: string;
    eventCharacLevel?: number;
    guildSecede?: number;
    startTime?: number;
    finishTime?: number;
    competitionArea?: number;
    competitionPeriod?: number;
    mercenaryStartTime?: number;
    mercenaryFinishTime?: number;
    mercenaryArea?: number;
    mercenaryPeriod?: number;
    vip?: string;
    jobName?: string;
    expertJobName?: string;
    mid?: string;
  };

  type CharacInfoQo = {
    current?: number;
    pageSize?: number;
    characNo?: number;
    characName?: string;
    online?: boolean;
    mid?: number;
  };

  type characSignParams = {
    characNo: string;
  };

  type cleanItemsParams = {
    characNo: string;
  };

  type cleanMailParams = {
    characNo: string;
  };

  type cleanRoleItemParams = {
    characNo: string;
    type: string;
  };

  type createAccountCargoParams = {
    characNo: string;
  };

  type CurrentUser = {
    uid?: number;
    accountname?: string;
    qq?: string;
    isAdmin?: boolean;
    menu?: Record<string, any>[];
    role?: string[];
    resource?: string[];
    mode?: string;
  };

  type DaBotConf = {
    id?: string;
    botNumber?: string;
    platform?: string;
    confKey?: string;
    confValue?: string;
    remark?: string;
  };

  type DaBotEventScript = {
    id?: string;
    scriptName?: string;
    eventType?: string;
    scriptType?: string;
    scriptContent?: string;
    remark?: string;
  };

  type DaBotEventScriptQo = {
    current?: number;
    pageSize?: number;
    scriptName?: string;
    eventType?: string;
    scriptType?: string;
    remark?: string;
  };

  type DaBotInfo = {
    id?: string;
    botNumber?: string;
    platform?: string;
    botSecret?: string;
    remark?: string;
    botType?: string;
    botUrl?: string;
    lastHeartbeatTime?: string;
    extData?: string;
  };

  type DaBotMessage = {
    id?: string;
    messageId?: string;
    platform?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    message?: string;
  };

  type DaBotMessageQo = {
    current?: number;
    pageSize?: number;
    messageId?: string;
    platform?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    message?: string;
  };

  type DaBotNotice = {
    id?: string;
    noticeType?: string;
    platform?: string;
    subType?: string;
    selfUser?: string;
    groupId?: string;
    operatorId?: string;
    userId?: string;
    selfTime?: string;
    messageId?: string;
  };

  type DaBotNoticeQo = {
    current?: number;
    pageSize?: number;
    noticeType?: string;
    platform?: string;
    subType?: string;
    selfUser?: string;
    groupId?: string;
    operatorId?: string;
    userId?: string;
    selfTime?: string;
    messageId?: string;
  };

  type DaBotQo = {
    current?: number;
    pageSize?: number;
    botNumber?: string;
    platform?: string;
    remark?: string;
    botType?: string;
    botUrl?: string;
  };

  type DaBotRequest = {
    id?: string;
    requestType?: string;
    platform?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    comment?: string;
    flag?: string;
  };

  type DaBotRequestQo = {
    current?: number;
    pageSize?: number;
    requestType?: string;
    platform?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    comment?: string;
    flag?: string;
  };

  type DaCdk = {
    cdkCode?: string;
    cdkType?: number;
    cdkConf?: string;
    remark?: string;
    createTime?: string;
    status?: boolean;
    createBy?: string;
    useTime?: string;
    useUser?: string;
    deleteFlag?: boolean;
    number?: string;
  };

  type DaCdkQo = {
    current?: number;
    pageSize?: number;
    cdkCode?: string;
    remark?: string;
    status?: boolean;
    createTime?: string;
    deleteFlag?: boolean;
  };

  type DaChannel = {
    id?: string;
    pid?: string;
    channelName?: string;
    fridaClient?: string;
    scriptContext?: string;
    mainPython?: string;
    channelStatus?: boolean;
    fridaStatus?: boolean;
    fridaJsonContext?: string;
  };

  type DaFridaFunction = {
    id?: string;
    childrenFunction?: string;
    functionName?: string;
    functionKey?: string;
    functionContext?: string;
    remark?: string;
    isSystemFun?: boolean;
  };

  type DaFridaScript = {
    id?: string;
    scriptName?: string;
    scriptContext?: string;
    childrenFunction?: string;
    remark?: string;
  };

  type DaGameConfig = {
    id?: string;
    confName?: string;
    confType?: number;
    confData?: string;
    confKey?: string;
    remark?: string;
    isSystemConf?: boolean;
  };

  type DaGameConfigQo = {
    current?: number;
    pageSize?: number;
    confName?: string;
    confType?: number;
    confKey?: string;
  };

  type DaGameEvent = {
    id?: string;
    accountId?: string;
    fileIndex?: number;
    fileName?: string;
    channel?: string;
    charcaNo?: string;
    charcaName?: string;
    level?: number;
    optionType?: string;
    param1?: string;
    param2?: string;
    param3?: string;
    clientIp?: string;
    optionTime?: string;
    optionInfo?: string;
  };

  type DaGameEventQo = {
    current?: number;
    pageSize?: number;
    accountId?: string;
    fileIndex?: number;
    channel?: string;
    charcaNo?: string;
    charcaName?: string;
    level?: number;
    optionType?: string;
    clientIp?: string;
  };

  type DaItemEntity = {
    id?: string;
    name?: string;
    type?: string;
    rarity?: string;
  };

  type DaItemQo = {
    current?: number;
    pageSize?: number;
    id?: string;
    name?: string;
    type?: string;
    rarity?: string;
  };

  type DaMailSendLog = {
    id?: string;
    sendDetails?: string;
    createTime?: string;
  };

  type DaNoticeSendLog = {
    id?: string;
    message?: string;
    createTime?: string;
  };

  type DaRole = {
    id?: string;
    roleName?: string;
    roleKey?: string;
    roleSort?: number;
    isDefault?: boolean;
    status?: boolean;
    remark?: string;
  };

  type DaRoleQo = {
    current?: number;
    pageSize?: number;
    roleName?: string;
    roleKey?: string;
    roleSort?: number;
    isDefault?: boolean;
    status?: boolean;
    remark?: string;
  };

  type DaSignInConf = {
    id?: string;
    configName?: string;
    configDate?: string;
    configJson?: string;
    remark?: string;
    createId?: number;
    createTime?: string;
    updateTime?: string;
    signInTime?: string;
  };

  type DaSignInConfDto = {
    id?: string;
    configName?: string;
    configDate?: string;
    configJson?: SignInConfigDate[];
    remark?: string;
    signInTime?: string;
  };

  type DaSignInConfQo = {
    current?: number;
    pageSize?: number;
    configName?: string;
  };

  type DaSignInConfVo = {
    id?: string;
    configName?: string;
    configDate?: string;
    configJson?: string;
    remark?: string;
    createTime?: string;
    updateTime?: string;
    signInTime?: string;
  };

  type DebugFridaDto = {
    channelId?: string;
    debugData?: string;
  };

  type disableAccountsParams = {
    uid: string;
  };

  type EditGameRoleItemDto = {
    characNo?: string;
    type?: string;
    gameItemVo?: GameItemVo;
  };

  type enableAccountsParams = {
    uid: string;
  };

  type EnableBotScriptDto = {
    botId?: string;
    scriptIds?: string[];
  };

  type FridaFunctionQo = {
    current?: number;
    pageSize?: number;
    functionName?: string;
    functionKey?: string;
    remark?: string;
    isSystemFun?: boolean;
  };

  type FridaScriptQo = {
    current?: number;
    pageSize?: number;
    scriptName?: string;
    remark?: string;
  };

  type GameItemVo = {
    index?: number;
    sealType?: number;
    itemType?: number;
    itemId?: number;
    itemName?: string;
    upgrade?: number;
    quality?: number;
    durability?: number;
    enchantment?: number;
    increaseType?: number;
    increaseLevel?: number;
    otherworldly?: number;
    forgeLevel?: number;
    ext20to30?: string[];
    ext33to36?: string[];
    ext37to50?: string[];
    ext52toEnd?: string[];
  };

  type gameRoleInfoParams = {
    characNo: string;
  };

  type gameRoleRecoverParams = {
    characNo: string;
  };

  type gameRoleRemoveParams = {
    characNo: string;
  };

  type generateCaptchaV1Params = {
    key?: string;
  };

  type generateCaptchaV2Params = {
    key?: string;
  };

  type getAccountCargoParams = {
    characNo: string;
  };

  type getAccountsParams = {
    id: string;
  };

  type getBotConfParams = {
    botNumber: string;
  };

  type getCdkInfoParams = {
    id: string;
  };

  type getChannelInfoParams = {
    id: string;
  };

  type getChildrenFunctionParams = {
    id: string;
  };

  type getConfInfoParams = {
    id: Record<string, any>;
  };

  type getDebugLogParams = {
    id: string;
  };

  type getEnableBotScriptParams = {
    id: string;
  };

  type getFridaFunctionInfoParams = {
    id: string;
  };

  type getFridaLogParams = {
    id: string;
  };

  type getFridaScriptInfoParams = {
    id: Record<string, any>;
  };

  type getOtherDataParams = {
    characNo: string;
  };

  type getRoleInfoParams = {
    id: Record<string, any>;
  };

  type getRoleItemParams = {
    characNo: string;
    type: string;
  };

  type infoBotParams = {
    id: string;
  };

  type infoBotScriptParams = {
    id: string;
  };

  type LicenseDetails = {
    type?: number;
    startTime?: string;
    endTime?: string;
  };

  type listItemParams = {
    name?: string;
  };

  type LoginDto = {
    userName: string;
    password: string;
    verificationCode?: string;
    captchaKey?: string;
  };

  type MailItemDto = {
    itemId?: string;
    itemType?: number;
    count?: string;
  };

  type OnlineCountVo = {
    count?: string;
    online?: string;
  };

  type openDungeonParams = {
    uid: string;
  };

  type openLeftAndRightParams = {
    characNo: string;
  };

  type OtherDataDto = {
    characNo?: string;
    win?: number;
    pvpPoint?: number;
    pvpGrade?: number;
    money?: string;
    sp?: number;
    tp?: number;
    qp?: number;
    avatarCoin?: number;
    oldData?: OtherDataDto;
  };

  type OtherDataVo = {
    characNo?: number;
    win?: number;
    pvpPoint?: number;
    pvpGrade?: number;
    money?: string;
    sp?: number;
    tp?: number;
    qp?: number;
    avatarCoin?: number;
  };

  type pageAccountsParams = {
    page: AccountsQo;
  };

  type PageQo = {
    current?: number;
    pageSize?: number;
  };

  type Postal = {
    postalId?: string;
    itemId?: string;
    occTime?: string;
    receiveCharacNo?: string;
    sendCharacNo?: string;
    sendCharacName?: string;
    addInfo?: string;
    endurance?: number;
    upgrade?: number;
    seperateUpgrade?: number;
    amplifyOption?: number;
    amplifyValue?: number;
    gold?: string;
    avataFlag?: number;
    unlimitFlag?: number;
    sealFlag?: number;
    creatureFlag?: number;
    letterId?: number;
    deleteFlag?: number;
    itemName?: string;
  };

  type RAccountCargo = {
    code?: number;
    data?: AccountCargo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RAccounts = {
    code?: number;
    data?: Accounts;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RBoolean = {
    code?: number;
    data?: boolean;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RCaptchaVo = {
    code?: number;
    data?: CaptchaVo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RCharacInfo = {
    code?: number;
    data?: CharacInfo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RCurrentUser = {
    code?: number;
    data?: CurrentUser;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RDaBotEventScript = {
    code?: number;
    data?: DaBotEventScript;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RDaBotInfo = {
    code?: number;
    data?: DaBotInfo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RDaCdk = {
    code?: number;
    data?: DaCdk;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RDaChannel = {
    code?: number;
    data?: DaChannel;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RDaFridaFunction = {
    code?: number;
    data?: DaFridaFunction;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RDaFridaScript = {
    code?: number;
    data?: DaFridaScript;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RDaGameConfig = {
    code?: number;
    data?: DaGameConfig;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RDaRole = {
    code?: number;
    data?: DaRole;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RDaSignInConfVo = {
    code?: number;
    data?: DaSignInConfVo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type rechargeBondsParams = {
    type?: number;
    uid: string;
    count?: string;
  };

  type RegDto = {
    userName: string;
    password: string;
    verificationCode?: string;
    captchaKey?: string;
  };

  type RegLicenseDto = {
    license?: string;
    verificationCode?: string;
    captchaKey?: string;
  };

  type removeAccountCargoParams = {
    characNo: string;
  };

  type removeBotConfParams = {
    id: string;
  };

  type removeGameEventParams = {
    id: Record<string, any>;
  };

  type removeItemsParams = {
    uiId: string;
  };

  type removeMailParams = {
    postalId: string;
  };

  type resetCreateRoleParams = {
    uid: string;
  };

  type resetPasswordParams = {
    uid: string;
    password: string;
  };

  type restartFridaParams = {
    id: string;
  };

  type RLicenseDetails = {
    code?: number;
    data?: LicenseDetails;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListAccounts = {
    code?: number;
    data?: Accounts[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListCharacInfo = {
    code?: number;
    data?: CharacInfo[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaBotConf = {
    code?: number;
    data?: DaBotConf[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaBotEventScript = {
    code?: number;
    data?: DaBotEventScript[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaBotInfo = {
    code?: number;
    data?: DaBotInfo[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaBotMessage = {
    code?: number;
    data?: DaBotMessage[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaBotNotice = {
    code?: number;
    data?: DaBotNotice[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaBotRequest = {
    code?: number;
    data?: DaBotRequest[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaCdk = {
    code?: number;
    data?: DaCdk[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaChannel = {
    code?: number;
    data?: DaChannel[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaFridaFunction = {
    code?: number;
    data?: DaFridaFunction[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaFridaScript = {
    code?: number;
    data?: DaFridaScript[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaGameConfig = {
    code?: number;
    data?: DaGameConfig[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaGameEvent = {
    code?: number;
    data?: DaGameEvent[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaItemEntity = {
    code?: number;
    data?: DaItemEntity[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaMailSendLog = {
    code?: number;
    data?: DaMailSendLog[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaNoticeSendLog = {
    code?: number;
    data?: DaNoticeSendLog[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaRole = {
    code?: number;
    data?: DaRole[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaSignInConf = {
    code?: number;
    data?: DaSignInConf[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListDaSignInConfVo = {
    code?: number;
    data?: DaSignInConfVo[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListLong = {
    code?: number;
    data?: string[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListPostal = {
    code?: number;
    data?: Postal[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListString = {
    code?: number;
    data?: string[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListTreeLong = {
    code?: number;
    data?: TreeLong[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RListUserItems = {
    code?: number;
    data?: UserItems[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RLong = {
    code?: number;
    data?: string;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RObject = {
    code?: number;
    data?: Record<string, any>;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type roleItemsParams = {
    characNo: string;
  };

  type RoleItemVo = {
    inventory?: GameItemVo[];
    equipslot?: GameItemVo[];
    creature?: GameItemVo[];
    cargo?: GameItemVo[];
    accountCargo?: GameItemVo[];
  };

  type roleListParams = {
    name?: string;
  };

  type roleMailPageParams = {
    characNo: string;
  };

  type RoleMailPageQo = {
    current?: number;
    pageSize?: number;
    scriptName?: string;
  };

  type roleResourceIdsParams = {
    roleId: string;
  };

  type roleResourceParams = {
    roleId: string;
  };

  type ROnlineCountVo = {
    code?: number;
    data?: OnlineCountVo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type ROtherDataVo = {
    code?: number;
    data?: OtherDataVo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RRoleItemVo = {
    code?: number;
    data?: RoleItemVo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type RString = {
    code?: number;
    data?: string;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: string;
  };

  type SendMailDto = {
    characNo?: string;
    title?: string;
    content?: string;
    gold?: string;
    itemList?: MailItemDto[];
  };

  type sendNoticeParams = {
    message: string;
  };

  type setMaxRoleParams = {
    uid: string;
  };

  type SignInConfigDate = {
    name?: string;
    itemId?: string;
    quantity?: string;
    itemType?: number;
  };

  type signInInfoParams = {
    id: string;
  };

  type signListParams = {
    characNo: number;
  };

  type stopFridaParams = {
    id: string;
  };

  type TreeLong = {
    name?: { empty?: boolean };
    id?: string;
    weight?: Record<string, any>;
    parentId?: string;
    config?: TreeNodeConfig;
    empty?: boolean;
  };

  type TreeNodeConfig = {
    idKey?: string;
    parentIdKey?: string;
    weightKey?: string;
    nameKey?: string;
    childrenKey?: string;
    deep?: number;
  };

  type UpdateScriptDto = {
    channelId?: string;
    context?: string;
    restartFrida?: boolean;
  };

  type UseCdkDto = {
    characNo?: string;
    cdks?: string[];
    verificationCode?: string;
    captchaKey?: string;
  };

  type UserItems = {
    uiId?: string;
    characNo?: string;
    slot?: number;
    itId?: string;
    expireDate?: string;
    obtainFrom?: number;
    regDate?: string;
    ipgAgencyNo?: string;
    abilityNo?: number;
    stat?: number;
    clearAvatarId?: number;
    jewelSocket?: string[];
    itemLockKey?: number;
    toIpgAgencyNo?: string;
    hiddenOption?: number;
    emblemEndurance?: number;
    color1?: number;
    color2?: number;
    tradeRestrict?: number;
    itemName?: string;
    mtime?: string;
  };
}
