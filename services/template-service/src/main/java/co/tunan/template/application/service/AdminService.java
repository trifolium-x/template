package co.tunan.template.application.service;

import co.tunan.template.application.model.vo.admin.AdminBaseVo;
import co.tunan.template.common.helper.BeanFiller;
import co.tunan.template.common.response.Paging;
import co.tunan.template.repo.entity.Admin;
import co.tunan.template.repo.mapper.AdminMapper;
import co.tunan.template.repo.util.QueryHelper;
import co.tunan.template.services.ms.common.model.ro.SearchRo;
import co.tunan.tucache.core.annotation.TuCache;
import co.tunan.tucache.core.annotation.TuCacheClear;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @title: AdminService
 * @author: trifolium
 * @date: 2023/9/8
 * @modified :
 */
@Slf4j
@Service
@Transactional
public class AdminService {

    @Inject
    private AdminMapper adminMapper;

    public Paging<AdminBaseVo> adminList(SearchRo searchRo) {

        QueryHelper.setupPageCondition(searchRo);
        return QueryHelper.getPaging(adminMapper.selectAll(), AdminBaseVo.class);
    }

    @TuCache(key = "admin:admin_detail:#{#id}")
    public AdminBaseVo adminById(Long id) {

        Admin admin = adminMapper.selectByPrimaryKey(id);
        if (admin == null) {
            return null;
        }
        return BeanFiller.target(AdminBaseVo.class)
                .accept(admin);
    }

    @TuCache(key = "admin:admin_list:cache:#{#ro.pageIndex}_#{#ro.pageSize}:#{#ro.keyword}", expire = 60)
    public Paging<AdminBaseVo> adminListHasCache(SearchRo ro) {

        QueryHelper.setupPageCondition(ro);
        return QueryHelper.getPaging(adminMapper.selectAll(), AdminBaseVo.class);
    }

    @TuCacheClear(keys = "admin")
    public void clearAllCache() {
    }
}
