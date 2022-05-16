package com.example.bank_kill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bank_kill.Dto.RecordDto;
import com.example.bank_kill.constant.ResponseConstant;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.mapper.UserMapper;
import com.example.bank_kill.model.Record;
import com.example.bank_kill.mapper.RecordMapper;
import com.example.bank_kill.model.User;
import com.example.bank_kill.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bank_kill.util.BaseResponsePackageUtil;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecordMapper recordMapper;

    public User getByUserId(Integer userID) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userID);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    public void check(RecordDto recordDto) {
        if (recordDto.getUserId() == null) throw new BankException("用户ID不能为空！");
        User user = getByUserId(recordDto.getUserId());
        if (user == null) throw new BankException("该用户不存在！");
        if (recordDto.getAmount() == null || recordDto.getAmount() <= 0) throw new BankException("金额错误！");
        if (recordDto.getOverdueDate() == null
                || recordDto.getOverdueDate().after(new Date(System.currentTimeMillis()))
        ) throw new BankException("日期不能为空！");
        if (recordDto.getOverdueTime() == null || recordDto.getOverdueTime() <= 0) throw new BankException("逾期时间错误！");
        if (recordDto.getIsblack() == null) throw new BankException("逾期是否为空！");
    }

    @Override
    public Map<String, Object> create(RecordDto recordDto) {
        check(recordDto);
        Record record = new Record();
        record.setUserId(recordDto.getUserId());
        record.setAmount(recordDto.getAmount());
        record.setOverdueDate(recordDto.getOverdueDate());
        record.setOverdueTime(recordDto.getOverdueTime());
        record.setIsblack(recordDto.getIsblack());
        record.setCreateTime(new Date(System.currentTimeMillis()));
        record.setModifyTime(new Date(System.currentTimeMillis()));
        recordMapper.insert(record);
        return ResponseConstant.V_ADD_SUCCESS;
    }

    @Override
    public Map<String, Object> delete(Integer recordId) {
        recordMapper.deleteById(recordId);
        return ResponseConstant.V_DELETE_SUCCESS;
    }

    @Override
    public Map<String, Object> update(Integer recordId, RecordDto recordDto) {
        check(recordDto);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("record_id", recordId);
        Record record = recordMapper.selectOne(queryWrapper);
        record.setUserId(recordDto.getUserId());
        record.setAmount(recordDto.getAmount());
        record.setOverdueDate(recordDto.getOverdueDate());
        record.setOverdueTime(recordDto.getOverdueTime());
        record.setIsblack(recordDto.getIsblack());
        record.setModifyTime(new Date(System.currentTimeMillis()));
        recordMapper.updateById(record);
        return ResponseConstant.V_UPDATE_SUCCESS;
    }

    @Override
    public Map<String, Object> getAllRecordInfo(Integer pageNum, Integer pageSize, String searchUserId) {
        LambdaQueryWrapper<Record> wrappers = Wrappers.<Record>lambdaQuery();
        if (!searchUserId.equals("null")) {
            Integer now = Integer.valueOf(searchUserId);
            wrappers.eq(Record::getUserId, now);
        }
        Page<Record> recordPage = recordMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        return BaseResponsePackageUtil.baseData(
                ImmutableMap.of(
                        "msg", "查找成功",
                        "res", recordPage
                )
        );
    }
}
