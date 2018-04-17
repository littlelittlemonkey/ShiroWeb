package com.lyc.controller;

import javax.servlet.http.HttpServletRequest;

import com.lyc.cache.JedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lyc.entity.User;



@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private JedisUtil jedisUtil;

	@RequestMapping("/login")
	public String login(User user,HttpServletRequest request){
		String name = user.getUserName();
		String password = user.getPassword();
//		使用MD5加密,例子中为了方便则不加密，直接铭文存储
//		String passwordAsMD5 = new Md5Hash(user.getPassword(),"salt").toString();
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(name, password);
		try{
			subject.login(token);
			Session session=subject.getSession();
			System.out.println("sessionId:"+session.getId());
			System.out.println("sessionHost:"+session.getHost());
			System.out.println("sessionTimeout:"+session.getTimeout());
			session.setAttribute("info", "登录");
			return "redirect:/success.jsp";
		}catch(AuthenticationException e){
			e.printStackTrace();
			System.out.println("----------------"+e.getMessage());
			request.setAttribute("user", user);
			request.setAttribute("errorMsg", "登录失败");
			return "index";
		}
	}
	

}
