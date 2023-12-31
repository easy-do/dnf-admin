declare namespace API {
  type Accounts = {
    uid?: string;
    accountname?: string;
    password?: string;
    qq?: string;
    dzuid?: number;
    billing?: number;
    vip?: string;
    admin?: boolean;
  };

  type AccountsQo = {
    current?: number;
    pageSize?: number;
    uid?: string;
    accountname?: string;
    qq?: string;
    billing?: number;
    vip?: string;
  };

  type allListParams = {
    name?: string;
  };

  type AuthRoleResourceDto = {
    roleId: string;
    resourceIds: string[];
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
    characNo?: number;
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
    mid?: number;
  };

  type characSignParams = {
    characNo: number;
  };

  type CurrentUser = {
    uid?: number;
    accountname?: string;
    qq?: string;
    billing?: number;
    vip?: string;
    isAdmin?: boolean;
    menu?: Record<string, any>[];
    role?: string[];
    resource?: string[];
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

  type enableAccountsParams = {
    uid: string;
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

  type getAccountsParams = {
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

  type getFridaFunctionInfoParams = {
    id: string;
  };

  type getFridaLogParams = {
    id: string;
  };

  type getFridaScriptInfoParams = {
    id: Record<string, any>;
  };

  type getRoleInfoParams = {
    id: Record<string, any>;
  };

  type listItemParams = {
    name?: string;
  };

  type LoginDto = {
    userName: string;
    password: string;
    verificationCode?: string;
  };

  type MailItemDto = {
    itemId?: string;
    count?: string;
  };

  type pageAccountsParams = {
    page: AccountsQo;
  };

  type PageQo = {
    current?: number;
    pageSize?: number;
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

  type RCurrentUser = {
    code?: number;
    data?: CurrentUser;
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
  };

  type resetPasswordParams = {
    uid: string;
    password: string;
  };

  type restartFridaParams = {
    id: string;
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

  type roleListParams = {
    name?: string;
  };

  type roleResourceIdsParams = {
    roleId: string;
  };

  type roleResourceParams = {
    roleId: string;
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
    characNo?: number;
    title?: string;
    content?: string;
    gold?: string;
    itemList?: MailItemDto[];
  };

  type sendNoticeParams = {
    message: string;
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
    parentId?: string;
    config?: TreeNodeConfig;
    weight?: Record<string, any>;
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
}
