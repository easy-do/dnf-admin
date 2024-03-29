package plus.easydo.dnf.service.impl;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.config.SystemConfig;
import plus.easydo.dnf.dto.AuthRoleResourceDto;
import plus.easydo.dnf.entity.DaResource;
import plus.easydo.dnf.entity.DaRole;
import plus.easydo.dnf.entity.DaRoleResource;
import plus.easydo.dnf.enums.SysTemModeEnum;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.mapper.DaResourceMapper;
import plus.easydo.dnf.service.IDaResourceService;
import plus.easydo.dnf.service.IDaRoleResourceService;
import plus.easydo.dnf.service.IDaRoleService;
import plus.easydo.dnf.service.IDaUserRoleService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static plus.easydo.dnf.entity.table.DaResourceTableDef.DA_RESOURCE;

/**
 * 系统资源 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class DaResourceServiceImpl extends ServiceImpl<DaResourceMapper, DaResource> implements IDaResourceService {

    private final IDaRoleResourceService roleResourceService;

    private final IDaRoleService roleService;

    private final IDaUserRoleService userRoleService;

    private final SystemConfig systemConfig;

    @Override
    public boolean authRoleResource(AuthRoleResourceDto authRoleResourceDto) {
        DaRole role = roleService.getById(authRoleResourceDto.getRoleId());
        if(Objects.isNull(role)){
            throw new BaseException("角色不存在");
        }
        if(role.getRoleKey().equals(systemConfig.getAdminRole())){
            throw new BaseException("不允许修改系统管理员");
        }
        ArrayList<DaRoleResource> entityList = new ArrayList<>();
        Long roleId = authRoleResourceDto.getRoleId();
        List<Long> resourceIds = authRoleResourceDto.getResourceIds();
        roleResourceService.removeByRoleId(roleId);
        resourceIds.forEach(resourceId -> entityList.add(DaRoleResource.builder().roleId(roleId).resourceId(resourceId).build()));
        return roleResourceService.saveBatch(entityList);
    }

    @Override
    public List<Tree<Long>> roleResource(Long roleId) {
        List<DaResource> menuList = getRoleResourceList(List.of(roleId));
        return buildResourceTree(menuList);
    }

    @Override
    public List<Long> roleResourceIds(Long roleId) {
        return getRoleResourceList(List.of(roleId)).stream().map(DaResource::getId).toList();
    }

    @Override
    public List<Tree<Long>> resourceTree() {
        QueryWrapper query = query().and(DA_RESOURCE.STATUS.eq(true));
        List<DaResource> allList = list(query);
        return buildResourceTree(allList);
    }

    @Override
    public List<String> userResource(Long userId) {
        List<Long> roles = userRoleService.userRoleIds(userId);
        if(roles.isEmpty()){
            return Collections.emptyList();
        }
        List<DaResource> resourceList = getRoleResourceList(roles);
        return resourceList.stream().map(DaResource::getResourceCode).toList();
    }
    @Override
    public List<Tree<Long>> userResource() {
        List<Long> roles = userRoleService.userRoleIds();
        if(roles.isEmpty()){
            return Collections.emptyList();
        }
        List<DaResource> resourceList = getRoleResourceForType(roles,"M");
        disableByMode(resourceList);
        return buildResourceTree(resourceList);
    }

    @Override
    public List<String> userResourceCodes() {
        List<Long> roles = userRoleService.userRoleIds();
        if(roles.isEmpty()){
            return Collections.emptyList();
        }
        List<DaResource> resourceList = getRoleResourceList(roles);
        disableByMode(resourceList);
        return resourceList.stream().map(DaResource::getResourceCode).toList();
    }

    private void disableByMode(List<DaResource> resourceList) {
        List<DaResource> removeList = new ArrayList<>();
        if(SysTemModeEnum.UTILS.getMode().equals(systemConfig.getMode())){
            List<String> utilsDisableResource = systemConfig.getUtilsDisableResource();
            resourceList.forEach(resource->{
                if(Objects.nonNull(resource.getResourceCode()) && utilsDisableResource.contains(resource.getResourceCode())){
                    removeList.add(resource);
                }
            });
        }
        if(SysTemModeEnum.STANDALONE.getMode().equals(systemConfig.getMode())){
            List<String> standaloneDisableResource = systemConfig.getStandaloneDisableResource();
            resourceList.forEach(resource->{
                if(Objects.nonNull(resource.getResourceCode()) && standaloneDisableResource.contains(resource.getResourceCode())){
                    removeList.add(resource);
                }
            });
        }
        removeList.forEach(resourceList::remove);
    }

    /**
     * 构建资源树
     *
     * @param resourceList resourceList
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Long>>
     * @author laoyu
     * @date 2023-11-14
     */
    private List<Tree<Long>> buildResourceTree(List<DaResource> resourceList) {
        if (resourceList.isEmpty()) {
            return ListUtil.empty();
        }
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        NodeParser<DaResource, Long> nodeParser = (daResource, treeNode) -> {
            treeNode.setId(daResource.getId());
            treeNode.setParentId(daResource.getParentId());
            treeNode.setName(daResource.getResourceName());
            treeNode.setWeight(daResource.getOrderNumber());
            treeNode.putExtra("path", daResource.getResourcePath());
        };
        return TreeUtil.build(resourceList, 0L, treeNodeConfig, nodeParser);
    }

     /**
      * 获取角色资源列表
      *
      * @param roleIds roleIds
      * @return java.util.List<plus.easydo.dnf.entity.DaResource>
      * @author laoyu
      * @date 2023-11-14
      */
    private List<DaResource> getRoleResourceList(List<Long> roleIds) {
        List<DaRoleResource> roleResources = roleResourceService.listByRoleIds(roleIds);
        if (roleResources.isEmpty()) {
            return ListUtil.empty();
        }
        List<Long> resourceIds = roleResources.stream().map(DaRoleResource::getResourceId).toList();
        QueryWrapper queryWrapper = query().and(DA_RESOURCE.ID.in(resourceIds)).and(DA_RESOURCE.STATUS.eq(true));
        return list(queryWrapper);
    }

    /**
     * 获取角色资源列表
     *
     * @param roleIds roleIds
     * @return java.util.List<plus.easydo.dnf.entity.DaResource>
     * @author laoyu
     * @date 2023-11-14
     */
    private List<DaResource> getRoleResourceForType(List<Long> roleIds,String resourceType) {
        List<DaRoleResource> roleResources = roleResourceService.listByRoleIds(roleIds);
        if (roleResources.isEmpty()) {
            return ListUtil.empty();
        }
        List<Long> resourceIds = roleResources.stream().map(DaRoleResource::getResourceId).toList();
        QueryWrapper queryWrapper = query()
                .and(DA_RESOURCE.ID.in(resourceIds))
                .and(DA_RESOURCE.STATUS.eq(true))
                .and(DA_RESOURCE.RESOURCE_TYPE.eq(resourceType));
        return list(queryWrapper);
    }
}
