package com.ywj.badminton.controller;

import com.ywj.badminton.model.Member;
import com.ywj.badminton.service.MemberService;
import com.ywj.badminton.utils.ResultMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService memberService;
    @GetMapping("/show/{stadiumId}")
    public ResultMessage show(@PathVariable String stadiumId){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setOther("members",memberService.show(stadiumId));
        resultMessage.setResult(true);
        return resultMessage;
    }
    @PatchMapping("/edit")
    public ResultMessage edit(@RequestBody Member member){
        try {
            memberService.edit(member);
            return ResultMessage.success("编辑成功");
        }catch (Exception e){
            return ResultMessage.failure("编辑失败");
        }
    }
    @PostMapping("/add")
    public ResultMessage add(@RequestBody Member member){
        try {
            memberService.add(member);
            return ResultMessage.success("新增会员信息成功");
        }catch (Exception e){
            return ResultMessage.failure("操作失败,该会员已存在");
        }
    }
    @DeleteMapping("/delete/{stadiumId}/{memberName}")
    public ResultMessage delete(@PathVariable String stadiumId, @PathVariable String memberName){
        memberService.delete(stadiumId,memberName);
        return ResultMessage.success("操作成功");
    }
}
