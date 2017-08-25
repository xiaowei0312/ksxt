package com.sxsram.ssm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sxsram.ssm.entity.Operation;
import com.sxsram.ssm.entity.Permission;
import com.sxsram.ssm.entity.Role;
import com.sxsram.ssm.entity.User;
import com.sxsram.ssm.entity.UserQueryVo;
import com.sxsram.ssm.service.UserService;
import com.sxsram.ssm.util.JsonResult;
import com.sxsram.ssm.util.MD5Util;
import com.sxsram.ssm.util.QueryCondition;
import com.sxsram.ssm.util.QueryConditionAbstractItem;
import com.sxsram.ssm.util.QueryConditionItem;
import com.sxsram.ssm.util.QueryConditionOp;
import com.sxsram.ssm.util.StringUtil;

@Controller()
@RequestMapping(value = "/user", method = { RequestMethod.GET, RequestMethod.POST })
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/main", method = { RequestMethod.GET, RequestMethod.POST })
	public String userCenter(HttpSession session, Model model, String code, String state) throws Exception {
		return "user/main";
	}

	@RequestMapping(value = "/loginPage", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginPage() throws Exception {
		return "user/login";
	}

	@RequestMapping(value = "/logOut", method = { RequestMethod.GET, RequestMethod.POST })
	public String logOut(HttpSession session) throws Exception {
		session.removeAttribute("user");
		return "user/login";
	}

	@RequestMapping(value = "/changePwd", method = { RequestMethod.GET, RequestMethod.POST })
	public String changePwd(HttpSession session, HttpServletRequest request, Model model, String oldPwd, String newPwd,
			String imgVerifyCode) {
		return "user/changePwd";
	}

	@RequestMapping(value = "/userStatics", method = { RequestMethod.GET, RequestMethod.POST })
	public String userStatics() throws Exception {
		return "user/userStatics";
	}

	@RequestMapping(value = "/systemOverview", method = { RequestMethod.GET, RequestMethod.POST })
	public String xtgk() throws Exception {
		return "user/systemOverview";
	}

	@RequestMapping(value = "/settingCenter", method = { RequestMethod.GET, RequestMethod.POST })
	public String pzzx() throws Exception {
		return "user/settingCenter";
	}

	@RequestMapping(value = "/roleManagement", method = { RequestMethod.GET, RequestMethod.POST })
	public String roleManagement() throws Exception {
		return "user/roleManagement";
	}

	@RequestMapping(value = "/permManagement", method = { RequestMethod.GET, RequestMethod.POST })
	public String permManagement() throws Exception {
		return "user/permManagement";
	}

	@RequestMapping(value = "/userGrantPermission", method = { RequestMethod.GET, RequestMethod.POST })
	public String userGrantPermission() throws Exception {
		return "user/userGrantPermission";
	}

	@RequestMapping(value = "/getUserOperations", method = { RequestMethod.GET, RequestMethod.POST })
	public String getUserOperations(HttpSession session, Model model, Integer id) throws Exception {
		id = id == null ? 1 : id;
		model.addAttribute("subActiveId", id);
		return "template/left";
	}

	@RequestMapping(value = "/getLoginInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public String getLoginInfo(HttpSession session, Model model, String code, String state) throws Exception {
		return "template/header";
	}

	@RequestMapping(value = "/footer", method = { RequestMethod.GET, RequestMethod.POST })
	public String footer(HttpSession session, Model model, String code, String state) throws Exception {
		return "template/footer";
	}

	@RequestMapping(value = "/loginSubmitAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String loginSubmit(HttpServletRequest request, HttpSession session, Model model, String username,
			String password, String verifyCode) {
		Gson gson = new Gson();
		JsonResult jsonResult = new JsonResult("0", "0");

		// 1.验证
		if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到对应的用户名和密码字段";
			return gson.toJson(jsonResult);
		}

		// 2.查找用户
		User user = null;
		try {
			UserQueryVo userQueryVo = new UserQueryVo();
			List<QueryConditionAbstractItem> items = new ArrayList<QueryConditionAbstractItem>();
			items.add(new QueryConditionItem("u.username", username, QueryConditionOp.EQ));
			items.add(new QueryConditionItem("u.password", MD5Util.MD5Encode(password, null), QueryConditionOp.EQ));
			userQueryVo.setQueryCondition(new QueryCondition(items));
			user = userService.findUser(userQueryVo);
			if (user == null) {
				return gson.toJson(new JsonResult("0", "1", "用户名或密码错误"));
			}
		} catch (Exception e) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}

		// 3.检查用户是否有登录权限
		List<Operation> operationList = new ArrayList<Operation>();
		for (Role role : user.getRoleList()) {
			for (Permission perm : role.getPermissionList()) {
				if (!operationList.contains(perm.getOperation()))
					operationList.add(perm.getOperation());
			}
		}
		if (operationList.size() == 0) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "您无权访问本系统";
			return gson.toJson(jsonResult);
		}

		// 4.整理Operation父子菜单
		List<Operation> operations = new ArrayList<Operation>();
		for (Operation operation : operationList) {
			if (operation.getMenu() == 1 && operation.getParentId() == null) {
				List<Operation> childOperations = new ArrayList<Operation>();
				for (Operation childOperation : operationList) {
					if (childOperation.getMenu() == 1 && childOperation.getParentId() == operation.getId()) {
						childOperations.add(childOperation);
					}
				}
				operation.setChildOperations(childOperations);
				operations.add(operation);
			}
		}

		session.setAttribute("user", user);
		session.setAttribute("operations", operations);
		// 获取被拦截器拦截的请求页面
		String url = (String) session.getAttribute("loginInterceptedReqUrl");
		if (url == null)
			url = request.getContextPath() + "/user/main.action";
		session.removeAttribute("loginInterceptedReqUrl");
		return gson.toJson(new JsonResult("0", "0", null, null, url));
	}

	// @RequestMapping(value = "/userOverview", method = { RequestMethod.GET,
	// RequestMethod.POST })
	// public String userOverview(Model model) throws Exception {
	// List<UserExpand> userExpands = userService.findAllUsers();
	// model.addAttribute("userList", userExpands);
	// return "user/userOverview";
	// }
	//
	// @RequestMapping(value = "/proxyManagement", method = { RequestMethod.GET,
	// RequestMethod.POST })
	// public String proxyManagement(Model model) throws Exception {
	// List<UserExpand> userExpands = userService.findAllProxyUsers();
	// model.addAttribute("userList", userExpands);
	// return "user/proxyManagement";
	// }
	//
	// @RequestMapping(value = "/sellerManagement", method = {
	// RequestMethod.GET, RequestMethod.POST })
	// public String sellerManagement(Model model, HttpSession session) throws
	// Exception {
	// UserExpand sessionUser = (UserExpand) session.getAttribute("user");
	//
	// List<UserExpand> userExpands = null;
	// if (sessionUser.getRole().getRoleName().equals("管理员")) {
	// userExpands = userService.findAllSeller();
	// } else {
	// userExpands = userService.findAllSellersByProxyId(sessionUser.getId());
	// }
	// model.addAttribute("userList", userExpands);
	// return "user/sellerManagement";
	// }
	//
	// @RequestMapping(value = "/managerManagement", method = {
	// RequestMethod.GET, RequestMethod.POST })
	// public String managerManagement(Model model) throws Exception {
	// List<UserExpand> userExpands = userService.findAllManagers();
	// model.addAttribute("userList", userExpands);
	// return "user/managerManagement";
	// }
	//
	// @RequestMapping(value = "/addNewUserAjax", method = { RequestMethod.GET,
	// RequestMethod.POST })
	// @ResponseBody
	// public String addNewUser(UserExpand userExpand) throws Exception {
	// Gson gson = new Gson();
	// if (userExpand == null) {
	// return gson.toJson(new JsonResult("0", "-1", "没有找到任何要添加的数据"));
	// }
	// // 1.检查用户名
	// String username = userExpand.getUsername();
	// if (username == null || username.equals("") ||
	// !username.matches("^[a-zA-z]{1}[A-Za-z0-9_]{5,15}$")) {
	// return gson.toJson(new JsonResult("0", "-1", "服务器消息:
	// 用户名必须以字母开头并且长度在6-16位之间"));
	// }
	// // 判断用户名是否存在
	// if (userService.usernameExist(username)) {
	// return gson.toJson(new JsonResult("0", "-1", "用户名已经被注册"));
	// }
	//
	// // 2.检查手机是否存在
	// String phone = userExpand.getPhone();
	// if (phone == null || phone.equals("") ||
	// !phone.matches("^0?(13|15|18|14|17)[0-9]{9}$")) {
	// return gson.toJson(new JsonResult("0", "-1", "服务器消息: 请输入正确的手机号码"));
	// }
	// // 判断电话号码是否存在
	// if (userService.phoneExist(phone)) {
	// return gson.toJson(new JsonResult("0", "-1", "手机号已被注册"));
	// }
	//
	// // 3.检查代理用户是否存在
	// if (userExpand.getProxyUser() != null) {
	// String keywords = userExpand.getProxyUser().getKeywords();
	// if (keywords != null && keywords.length() > 0) {
	// UserExpand proxyUser = userService.findUserByKeyWords(keywords);
	// if (proxyUser == null) {
	// return gson.toJson(new JsonResult("0", "-1", "代理商不存在"));
	// }
	// userExpand.setProxyUser(proxyUser);
	// } else {
	// userExpand.setProxyUser(null);
	// }
	// }
	//
	// // 4.密码判断
	// if (userExpand.getPassword() == null || userExpand.getPassword().length()
	// == 0)
	// userExpand.setPassword(ConfigUtil.RESETPWD);
	//
	// // 5.插入数据库
	// boolean b = userService.registUser(userExpand);
	// if (b) {
	// return gson.toJson(new JsonResult("0", "0", null, null, null));
	// }
	// return gson.toJson(new JsonResult("0", "-1", "添加用户失败：Unknown Reason",
	// null, null));
	// }
	//
	// @RequestMapping(value = "/getAllRoles", method = { RequestMethod.GET,
	// RequestMethod.POST })
	// @ResponseBody
	// public String getAllRoles(UserExpand userExpand) throws Exception {
	// Gson gson = new Gson();
	// List<Role> roles = userService.findAllRoles();
	// return gson.toJson(roles);
	// }
	//
	// @RequestMapping(value = "/updateUserAjax", method = { RequestMethod.GET,
	// RequestMethod.POST })
	// @ResponseBody
	// public String updateUser(UserExpand userExpand, String proxy) throws
	// Exception {
	// Gson gson = new Gson();
	// if (userExpand == null) {
	// return gson.toJson(new JsonResult("0", "-1", "找不到任何更新数据."));
	// }
	//
	// // 1.检查用户名
	// String username = userExpand.getUsername();
	// // if (username == null || username.equals("") ||
	// // !username.matches("^[a-zA-z]{1}[A-Za-z0-9_]{5,15}$")) {
	// // return gson.toJson(new JsonResult("0", "-1",
	// // "服务器消息:用户名必须以字母开头并且长度在6-16位之间"));
	// // }
	// // 判断用户名是否存在
	// if (username != null) {
	// if (!userService.usernameExist(username)) {
	// return gson.toJson(new JsonResult("0", "-1", "用户名不存在，请勿恶意访问."));
	// }
	// }
	//
	// // 2.检查手机是否存在
	// String phone = userExpand.getPhone();
	// // if (phone == null || phone.equals("") ||
	// // !phone.matches("^0?(13|15|18|14|17)[0-9]{9}$")) {
	// // return gson.toJson(new JsonResult("0", "-1", "服务器消息: 请输入正确的手机号码"));
	// // }
	// // 判断电话号码是否存在
	//
	// if (phone != null) {
	// if (!userService.phoneExist(phone)) {
	// return gson.toJson(new JsonResult("0", "-1", "手机号不存在，请勿恶意访问."));
	// }
	// }
	//
	// Integer id = userExpand.getId();
	// if (id == null && username == null && phone == null) {
	// return gson.toJson(new JsonResult("0", "-1",
	// "至少提供id、usernmae、phone其中一种作为更新依据"));
	// }
	//
	// if (userExpand.getProxyUser() != null) {
	// String keywords = userExpand.getProxyUser().getKeywords();
	// if (keywords != null && keywords.length() > 0) {
	// UserExpand proxyUser = userService.findUserByKeyWords(keywords);
	// if (proxyUser == null) {
	// return gson.toJson(new JsonResult("0", "-1", "代理商不存在"));
	// }
	// userExpand.setProxyUser(proxyUser);
	// } else {
	// userExpand.setProxyUser(null);
	// }
	// }
	//
	// // 3.-1检查
	// String province, city, area;
	// province = userExpand.getProvince();
	// city = userExpand.getCity();
	// area = userExpand.getArea();
	// if (userExpand.getRole() != null) {
	// Integer roleId = userExpand.getRole().getId();
	// if (roleId != null && roleId == -1)
	// userExpand.getRole().setId(null);
	// }
	// if (province != null && province.equals("-1"))
	// userExpand.setProvince(null);
	// if (city != null && city.equals("-1"))
	// userExpand.setCity(null);
	// if (area != null && area.equals("-1"))
	// userExpand.setArea(null);
	//
	// // 3.插入数据库
	// boolean b = userService.updateUser(userExpand);
	// if (b) {
	// return gson.toJson(new JsonResult("0", "0", null, null, null));
	// }
	// return gson.toJson(new JsonResult("0", "-1", "更新用户失败：Unknown Reason",
	// null, null));
	// }
	//
	// @RequestMapping(value = "/deleteUserAjax", method = { RequestMethod.GET,
	// RequestMethod.POST })
	// @ResponseBody
	// public String deleteUser(UserExpand userExpand) throws Exception {
	// Gson gson = new Gson();
	// // 3.插入数据库
	// boolean b = userService.deleteUser(userExpand);
	// if (b) {
	// return gson.toJson(new JsonResult("0", "0", null, null, null));
	// }
	// return gson.toJson(new JsonResult("0", "-1", "删除用户失败：Unknown Reason",
	// null, null));
	// }
	//
	// @RequestMapping(value = "/resetUserPwdAjax", method = {
	// RequestMethod.GET, RequestMethod.POST })
	// @ResponseBody
	// public String resetUserPwd(UserExpand userExpand) throws Exception {
	// Gson gson = new Gson();
	// userExpand.setPassword(ConfigUtil.RESETPWD);
	// // 3.插入数据库
	// boolean b = userService.resetUserPwd(userExpand);
	// if (b) {
	// return gson.toJson(new JsonResult("0", "0", null, null, null));
	// }
	// return gson.toJson(new JsonResult("0", "-1", "重置密码失败：Unknown Reason",
	// null, null));
	// }

	@RequestMapping(value = "/changePwdValidate1", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String changePwdValidate1(HttpSession session, Model model, String oldPwd) {
		JsonResult jsonResult = new JsonResult("0", "0");
		Gson gson = new Gson();
		// 1.判断是否超时登录
		User userExpand = (User) session.getAttribute("user");
		if (userExpand == null) {
			jsonResult.logicCode = "2";
			jsonResult.resultMsg = "登录超时，请重新登录";
			return gson.toJson(jsonResult);
		}

		if (!userExpand.getPassword().equals(MD5Util.MD5Encode(oldPwd, null))) {
			jsonResult.logicCode = "1";
			jsonResult.resultMsg = "原始密码不正确";
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/changePwdValidate2", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String changePwdValidate2(HttpSession session, HttpServletRequest request, Model model, String oldPwd,
			String newPwd, String imgVerifyCode) {
		JsonResult jsonResult = new JsonResult("0", "0");
		jsonResult.url = request.getContextPath() + "/user/loginPage.action";
		Gson gson = new Gson();

		// 1.判断是否超时登录
		User userExpand = (User) session.getAttribute("user");
		if (userExpand == null) {
			jsonResult.logicCode = "2";
			jsonResult.resultMsg = "登录超时，请重新登录";
			return gson.toJson(jsonResult);
		}

		// 2.判断验证码是否正确
		String savedImgCode = (String) session.getAttribute("imageVerifyCode");
		if (savedImgCode == null || !savedImgCode.equals(imgVerifyCode)) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "验证码不正确";
			return gson.toJson(jsonResult);
		}

		// 3.判断原始密码是否正确
		if (!MD5Util.MD5Encode(oldPwd, null).equals(userExpand.getPassword())) {
			jsonResult.logicCode = "1";
			jsonResult.resultMsg = "原始密码不正确";
			return gson.toJson(jsonResult);
		}

		// 3.修改密码
		User newUser = new User();
		newUser.setId(userExpand.getId());
		newUser.setPassword(newPwd);
		try {
			userService.updateUser(newUser);
			session.removeAttribute("user");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(jsonResult);
	}

}
