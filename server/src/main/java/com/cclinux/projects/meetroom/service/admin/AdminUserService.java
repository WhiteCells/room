package com.cclinux.projects.meetroom.service.admin;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cclinux.framework.core.domain.PageParams;
import com.cclinux.framework.core.domain.PageResult;
import com.cclinux.framework.core.mapper.UpdateWhere;
import com.cclinux.framework.core.mapper.Where;
import com.cclinux.framework.helper.FileHelper;
import com.cclinux.framework.helper.FormHelper;
import com.cclinux.projects.meetroom.comm.ProjectConfig;
import com.cclinux.projects.meetroom.mapper.UserMapper;
import com.cclinux.projects.meetroom.model.UserModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Notes: 用户管理业务逻辑
 * @Author: cclinux0730 (weixin)
 * @Date: 2024/12/15 12:10
 * @Ver: ccminicloud-framework 3.2.1
 */


// @Service("MeetRoomAdminUserService")
// public class AdminUserService extends BaseMyAdminService {


//     @Resource(name = "MeetRoomUserMapper")
//     private UserMapper userMapper;


//     /**
//      * 用户列表
//      */
//     public PageResult getAdminUserList(PageParams pageRequest) {

//         Where<UserModel> where = new Where<>();

//         // 关键字查询
//         String search = pageRequest.getSearch();
//         if (StrUtil.isNotEmpty(search)) {
//             where.and(wrapper -> {
//                 wrapper.or().like("USER_NAME", search);
//                 wrapper.or().like("USER_ACCOUNT", search);
//             });
//         }

//         // 条件查询
//         String sortType = pageRequest.getSortType();
//         String sortVal = pageRequest.getSortVal();
//         if (StrUtil.isNotEmpty(sortType) && StrUtil.isNotEmpty(sortVal)) {
//             switch (sortType) {
//                 case "status": {
//                     where.eq("USER_STATUS", Convert.toInt(sortVal));
//                     break;
//                 }
//                 case "sort": {
//                     where.fmtOrderBySort(sortVal, "");
//                     break;
//                 }
//             }

//         }

//         // 排序
//         where.orderByDesc("USER_ID");


//         Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
//         return userMapper.getPageList(page, where, "*");
//     }

//     /**
//      * 删除用户
//      */
//     public void delUser(long id) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//     }


//     /**
//      * 获取单个用户
//      */
//     public Map<String, Object> getUserDetail(long id) {
//         return userMapper.getOneMap(id);
//     }

//     /**
//      * 修改用户状态
//      */
//     public void statusUser(long id, int status) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//     }


//     /**
//      * 重置用户密码
//      */
//     public void editUserPwd(long id, String pwd) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");
//     }


//     /**
//      * 导出名单
//      */
//     public Map<String, Object> exportUserJoinExcel(String fmt) {
//         appError("{校园自习}该功能暂不开放，如有需要请加作者微信：cclinux0730");

//         return null;
//     }


// }


@Service("MeetRoomAdminUserService")
public class AdminUserService extends BaseMyAdminService {

    @Resource(name = "MeetRoomUserMapper")
    private UserMapper userMapper;

    /**
     * 用户列表
     */
    public PageResult getAdminUserList(PageParams pageRequest) {
        Where<UserModel> where = new Where<>();

        // 关键字查询
        String search = pageRequest.getSearch();
        if (StrUtil.isNotEmpty(search)) {
            where.and(wrapper -> {
                wrapper.or().like("USER_NAME", search);
                wrapper.or().like("USER_ACCOUNT", search);
            });
        }

        // 条件查询
        String sortType = pageRequest.getSortType();
        String sortVal = pageRequest.getSortVal();
        if (StrUtil.isNotEmpty(sortType) && StrUtil.isNotEmpty(sortVal)) {
            switch (sortType) {
                case "status": {
                    where.eq("USER_STATUS", Convert.toInt(sortVal));
                    break;
                }
                case "sort": {
                    where.fmtOrderBySort(sortVal, "");
                    break;
                }
            }
        }

        // 排序
        where.orderByDesc("USER_ID");

        Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
        return userMapper.getPageList(page, where, "*");
    }

    /**
     * 删除用户
     */
    public void delUser(long id) {
        // 假设使用软删除，我们只需更新用户状态
        UpdateWhere updateWhere = new UpdateWhere();
        updateWhere.eq("USER_ID", id);
        updateWhere.set("USER_STATUS", -1);  // 假设 -1 表示已删除
        userMapper.update(null, updateWhere);
        // 如果需要硬删除，可以直接删除
        // userMapper.deleteById(id);
    }

    /**
     * 获取单个用户
     */
    public Map<String, Object> getUserDetail(long id) {
        // Make sure the return type is Map<String, Object>
        return userMapper.getOneMap(id);
    }

    /**
     * 修改用户状态
     */
    public void statusUser(long id, int status) {
        UpdateWhere updateWhere = new UpdateWhere();
        updateWhere.eq("USER_ID", id);
        updateWhere.set("USER_STATUS", status);  // 修改状态
        userMapper.update(null, updateWhere);
    }

    /**
     * 重置用户密码
     */
    public void editUserPwd(long id, String pwd) {
        // 假设使用 MD5 加密密码
        String hashedPwd = DigestUtil.md5Hex(pwd);
        UpdateWhere updateWhere = new UpdateWhere();
        updateWhere.eq("USER_ID", id);
        updateWhere.set("USER_PASSWORD", hashedPwd);  // 修改密码
        userMapper.update(null, updateWhere);
    }

    /**
     * 导出名单
     */
    public Map<String, Object> exportUserJoinExcel(String fmt) {
        // 查询用户数据
        List<UserModel> userList = userMapper.selectList(new Where<UserModel>().eq("USER_STATUS", 1));  // 查询所有正常状态的用户
        // if (userList == null || userList.isEmpty()) {
        //     return MapUtil.builder().put("message", "No data to export").build();
        // }

        // 创建 Excel 写入对象
        ExcelWriter writer = ExcelUtil.getWriter();
        // 添加标题
        writer.addHeaderAlias("USER_ID", "用户ID");
        writer.addHeaderAlias("USER_NAME", "用户名");
        writer.addHeaderAlias("USER_ACCOUNT", "账户");
        writer.addHeaderAlias("USER_STATUS", "状态");

        // 将数据写入 Excel
        writer.write(userList);

        // 导出文件名
        String fileName = "UserList." + fmt;

        // 返回 Map<String, Object>
        Map<String, Object> result = new HashMap<>();
        result.put("fileName", fileName);

        // 以字节数组形式获取 Excel 内容
        // ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // writer.flush(outputStream);
        // result.put("fileContent", outputStream.toByteArray());

        // 关闭 writer
        writer.close();

        return result;
    }
}
