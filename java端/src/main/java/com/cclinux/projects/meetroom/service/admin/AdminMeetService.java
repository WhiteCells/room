package com.cclinux.projects.meetroom.service.admin;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cclinux.framework.core.domain.PageParams;
import com.cclinux.framework.core.domain.PageResult;
import com.cclinux.framework.core.mapper.UpdateWhere;
import com.cclinux.framework.core.mapper.Where;
import com.cclinux.framework.exception.AppException;
import com.cclinux.framework.helper.FileHelper;
import com.cclinux.framework.helper.FormHelper;
import com.cclinux.framework.helper.TimeHelper;
import com.cclinux.projects.meetroom.comm.ProjectConfig;
import com.cclinux.projects.meetroom.mapper.MeetJoinMapper;
import com.cclinux.projects.meetroom.mapper.MeetMapper;
import com.cclinux.projects.meetroom.model.MeetJoinModel;
import com.cclinux.projects.meetroom.model.MeetModel;
import com.cclinux.projects.meetroom.service.cust.MeetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Notes: 活动业务逻辑
 * @Author: cclinux0730 (weixin)
 * @Date: 2024/7/15 12:10
 * @Ver: ccminicloud-framework 3.2.1
 */


// @Service("MeetRoomAdminMeetService")
// public class AdminMeetService extends BaseMyAdminService {


//     @Resource(name = "MeetRoomMeetMapper")
//     private MeetMapper meetMapper;

//     @Resource(name = "MeetRoomMeetJoinMapper")
//     private MeetJoinMapper meetJoinMapper;

//     @Resource(name = "MeetRoomMeetService")
//     private MeetService meetService;


//     /**
//      * 添加活动
//      */
//     public long insertMeet(MeetModel meet) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//         return 0;
//     }

//     /**
//      * 修改活动
//      */
//     public void editMeet(MeetModel meet) {

//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//     }

//     /**
//      * 设置预约天数
//      */
//     public void setMeetDays(long meetId, String days) {

//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");

//     }


//     /**
//      * 活动列表
//      */
//     public PageResult getAdminMeetList(PageParams pageRequest) {

//         Where<MeetModel> where = new Where<>();

//         // 关键字查询
//         String search = pageRequest.getSearch();
//         if (StrUtil.isNotEmpty(search)) {
//             where.and(wrapper -> {
//                 wrapper.or().like("MEET_TITLE", search);
//             });
//         }

//         // 条件查询
//         String sortType = pageRequest.getSortType();
//         String sortVal = pageRequest.getSortVal();
//         if (StrUtil.isNotEmpty(sortType) && StrUtil.isNotEmpty(sortVal)) {
//             switch (sortType) {
//                 case "cateId": {
//                     where.eq("MEET_CATE_ID", Convert.toLong(sortVal));
//                     break;
//                 }
//                 case "status": {
//                     where.eq("MEET_STATUS", Convert.toInt(sortVal));
//                     break;
//                 }
//                 case "vouch": {
//                     where.eq("MEET_VOUCH", 1);
//                     break;
//                 }
//                 case "top": {
//                     where.eq("MEET_ORDER", 0);
//                     break;
//                 }
//                 case "sort": {
//                     logger.info("SortVal" + sortVal);
//                     where.fmtOrderBySort(sortVal, "");
//                     break;
//                 }
//             }

//         }

//         // 排序
//         where.orderByAsc("MEET_ORDER");
//         where.orderByDesc("MEET_ID");


//         Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
//         return meetMapper.getPageList(page, where, "*");
//     }

//     /**
//      * 活动报名名单
//      */
//     public PageResult getAdminMeetJoinList(PageParams pageRequest) {

//         Where<MeetJoinModel> where = new Where<>();


//         long meetId = pageRequest.getParamLong("meetId");
//         where.eq("MEET_JOIN_MEET_ID", meetId);

//         // 关键字查询
//         String search = pageRequest.getSearch();
//         if (StrUtil.isNotEmpty(search)) {
//             if (search.length() == 21 && search.contains("#")) {
//                 // 日期查询
//                 String[] arr = search.split("#");
//                 where.between("MEET_JOIN_DAY", arr[0], arr[1]);
//             } else {
//                 where.and(wrapper -> {
//                     wrapper.or().like("MEET_JOIN_OBJ", search);
//                 });
//             }

//         }

//         // 条件查询
//         String sortType = pageRequest.getSortType();
//         String sortVal = pageRequest.getSortVal();
//         if (StrUtil.isNotEmpty(sortType) && StrUtil.isNotEmpty(sortVal)) {
//             switch (sortType) {
//                 case "check": {
//                     where.eq("MEET_JOIN_IS_CHECK", Convert.toInt(sortVal));
//                     break;
//                 }
//                 case "status": {
//                     where.eq("MEET_JOIN_STATUS", Convert.toInt(sortVal));
//                     break;
//                 }
//                 case "sort": {
//                     logger.info("SortVal" + sortVal);
//                     where.fmtOrderBySort(sortVal, "");
//                     break;
//                 }
//             }

//         }

//         // 排序
//         where.orderByDesc("MEET_JOIN_ID");


//         Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
//         return meetJoinMapper.getPageList(page, where, "*");
//     }

//     /**
//      * 删除活动
//      */
//     public void delMeet(long id) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");

//     }

//     /**
//      * 删除活动报名
//      */
//     public void delMeetJoin(long meetJoinId) {

//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");

//     }


//     /**
//      * 获取单个预约
//      */
//     public Map<String, Object> getMeetDetail(long id) {
//         return meetMapper.getOneMap(id);
//     }

//     /**
//      * 修改预约状态
//      */
//     public void statusMeet(long id, int status) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//     }


//     /**
//      * 管理员按钮核销
//      */
//     public void checkinMeetJoin(long meetJoinId, int flag) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//     }

//     /**
//      * 修改排序
//      */
//     public void orderMeet(long id, int order) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//     }


//     /**
//      * 首页设定
//      */
//     public void vouchMeet(long id, int vouch) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//     }

//     /**
//      * 导出名单
//      */
//     public Map<String, Object> exportMeetJoinExcel(long meetId, String start, String end) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//         return null;
//     }

//     /**
//      * 清空名单
//      */
//     public void clearMeetAll(long meetId) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//     }

//     /**
//      * 管理员扫码核销
//      */
//     public void scanMeetJoin(long meetId, String code) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//     }

// }


@Service("MeetRoomAdminMeetService")
public class AdminMeetService {

    @Resource(name = "MeetRoomMeetMapper")
    private MeetMapper meetMapper;

    @Resource(name = "MeetRoomMeetJoinMapper")
    private MeetJoinMapper meetJoinMapper;

    @Resource(name = "MeetRoomMeetService")
    private MeetService meetService;

    /**
     * 添加活动
     */
    public long insertMeet(MeetModel meet) {
        if (meet == null) {
            throw new AppException("活动信息不能为空");
        }
        meet.setMeetStatus(1);  // 设置默认状态为正常
        // meet.setMeetCreateTime(TimeHelper.getCurrentTime());  // 设置创建时间
        meetMapper.insert(meet);
        return meet.getMeetId();
    }

    /**
     * 修改活动
     */
    public void editMeet(MeetModel meet) {
        if (meet == null || meet.getMeetId() == null) {
            throw new AppException("活动ID不能为空");
        }
        MeetModel existingMeet = meetMapper.selectById(meet.getMeetId());
        if (existingMeet == null) {
            throw new AppException("活动不存在");
        }
        meetMapper.updateById(meet);
    }

    /**
     * 获取活动列表
     */
    public PageResult getAdminMeetList(PageParams pageRequest) {
        Where<MeetModel> where = new Where<>();

        String search = pageRequest.getSearch();
        if (StrUtil.isNotEmpty(search)) {
            where.and(wrapper -> wrapper.or().like("MEET_TITLE", search));
        }

        String sortType = pageRequest.getSortType();
        String sortVal = pageRequest.getSortVal();
        if (StrUtil.isNotEmpty(sortType) && StrUtil.isNotEmpty(sortVal)) {
            switch (sortType) {
                case "cateId":
                    where.eq("MEET_CATE_ID", Convert.toLong(sortVal));
                    break;
                case "status":
                    where.eq("MEET_STATUS", Convert.toInt(sortVal));
                    break;
                case "vouch":
                    where.eq("MEET_VOUCH", 1);
                    break;
                case "top":
                    where.eq("MEET_ORDER", 0);
                    break;
                case "sort":
                    where.fmtOrderBySort(sortVal, "");
                    break;
            }
        }

        where.orderByAsc("MEET_ORDER");
        where.orderByDesc("MEET_ID");

        Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
        return meetMapper.getPageList(page, where, "*");
    }

    /**
     * 获取活动报名名单
     */
    public PageResult getAdminMeetJoinList(PageParams pageRequest) {
        Where<MeetJoinModel> where = new Where<>();

        long meetId = pageRequest.getParamLong("meetId");
        where.eq("MEET_JOIN_MEET_ID", meetId);

        String search = pageRequest.getSearch();
        if (StrUtil.isNotEmpty(search)) {
            if (search.length() == 21 && search.contains("#")) {
                String[] arr = search.split("#");
                where.between("MEET_JOIN_DAY", arr[0], arr[1]);
            } else {
                where.and(wrapper -> wrapper.or().like("MEET_JOIN_OBJ", search));
            }
        }

        String sortType = pageRequest.getSortType();
        String sortVal = pageRequest.getSortVal();
        if (StrUtil.isNotEmpty(sortType) && StrUtil.isNotEmpty(sortVal)) {
            switch (sortType) {
                case "check":
                    where.eq("MEET_JOIN_IS_CHECK", Convert.toInt(sortVal));
                    break;
                case "status":
                    where.eq("MEET_JOIN_STATUS", Convert.toInt(sortVal));
                    break;
                case "sort":
                    where.fmtOrderBySort(sortVal, "");
                    break;
            }
        }

        where.orderByDesc("MEET_JOIN_ID");

        Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
        return meetJoinMapper.getPageList(page, where, "*");
    }

    /**
     * 删除活动
     */
    public void delMeet(long id) {
        MeetModel meet = meetMapper.selectById(id);
        if (meet == null) {
            throw new AppException("活动不存在");
        }
        UpdateWhere updateWhere = new UpdateWhere();
        updateWhere.eq("MEET_ID", id);
        updateWhere.set("MEET_STATUS", -1);  // -1 表示已删除
        meetMapper.update(null, updateWhere);
    }

    /**
     * 删除活动报名
     */
    public void delMeetJoin(long meetJoinId) {
        MeetJoinModel meetJoin = meetJoinMapper.selectById(meetJoinId);
        if (meetJoin == null) {
            throw new AppException("报名记录不存在");
        }
        meetJoinMapper.deleteById(meetJoinId);
    }

    /**
     * 导出活动报名名单为Excel
     */
    public Map<String, Object> exportMeetJoinExcel(long meetId, String start, String end) {
        List<MeetJoinModel> meetJoinList = meetJoinMapper.selectList(new Where<MeetJoinModel>()
                .eq("MEET_JOIN_MEET_ID", meetId)
                .between("MEET_JOIN_DAY", start, end));

        if (meetJoinList == null || meetJoinList.isEmpty()) {
            return new HashMap<String, Object>() {{
                put("message", "No data to export");
            }};
        }

        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("MEET_JOIN_ID", "报名ID");
        writer.addHeaderAlias("MEET_JOIN_OBJ", "报名对象");
        writer.addHeaderAlias("MEET_JOIN_DAY", "报名日期");
        writer.addHeaderAlias("MEET_JOIN_STATUS", "状态");

        writer.write(meetJoinList);

        String fileName = "MeetJoinList.xlsx";
        Map<String, Object> result = new HashMap<>();
        result.put("fileName", fileName);

        // ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // writer.flush(outputStream);
        // result.put("fileContent", outputStream.toByteArray());

        writer.close();
        return result;
    }

    /**
     * 清空活动报名名单
     */
    public void clearMeetAll(long meetId) {
        meetJoinMapper.delete(new Where<MeetJoinModel>().eq("MEET_JOIN_MEET_ID", meetId));
    }

public void setMeetDays(long meetId, String days) {
    if (meetId <= 0) {
        throw new AppException("活动ID无效");
    }

    // 根据活动ID获取活动信息
    MeetModel meet = meetMapper.selectById(meetId);
    if (meet == null) {
        throw new AppException("活动不存在");
    }

    // 如果传入的days是空的，抛出异常
    if (StrUtil.isEmpty(days)) {
        throw new AppException("预约天数不能为空");
    }

    // 解析days字符串，假设格式为 "startDate#endDate"
    String[] dateRange = days.split("#");
    if (dateRange.length != 2) {
        throw new AppException("日期格式错误，正确格式为 startDate#endDate");
    }

    String startDate = dateRange[0].trim();
    String endDate = dateRange[1].trim();

    // 校验日期格式（可以使用正则表达式或日期解析来验证）
    // if (!TimeHelper.isValidDate(startDate) || !TimeHelper.isValidDate(endDate)) {
    //     throw new AppException("日期格式无效");
    // }

    // 设置活动的预约天数范围
    meet.setMeetDays(startDate);

    // 更新活动信息
    meetMapper.updateById(meet);
}

}