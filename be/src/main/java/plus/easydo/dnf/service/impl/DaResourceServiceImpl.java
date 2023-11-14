package plus.easydo.dnf.service.impl;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.dto.AuthRoleResourceDto;
import plus.easydo.dnf.entity.DaRoleResource;
import plus.easydo.dnf.service.IDaResourceService;
import plus.easydo.dnf.entity.DaResource;
import plus.easydo.dnf.mapper.DaResourceMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import plus.easydo.dnf.service.IDaRoleResourceService;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean authRoleResource(AuthRoleResourceDto authRoleResourceDto) {
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
            treeNode.putExtra("details", daResource);
            treeNode.putExtra("key", daResource.getUrl());
            treeNode.putExtra("type", daResource.getResourceType());
            treeNode.putExtra("resourceCode", daResource.getResourceCode());
            treeNode.putExtra("visible", daResource.getStatus());
        };
        Long min = resourceList.stream().min((a, b) -> (int) (a.getParentId() - b.getParentId())).get().getParentId();
        return TreeUtil.build(resourceList, min, treeNodeConfig, nodeParser);
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
}
