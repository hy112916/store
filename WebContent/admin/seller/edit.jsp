<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
	</HEAD>
	
	<body>
		<%
		String aid =request.getParameter("aid");
		String adminname =request.getParameter("adminname");
		String password =request.getParameter("password");
		String telephone =request.getParameter("telephone");
		String email =request.getParameter("email");
		String cid =request.getParameter("cid");
		%>
		<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath}/admin" method="post">
			<input type="hidden" name="method" value="update">
			&nbsp;
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>编辑销售员</STRONG>
						</strong>
					</td>
				</tr>

				<tr>
				    <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						销售员id：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="aid" value=<%=aid %> id="userAction_save_do_logonName" readonly="readonly" class="bg"/>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						销售员名称：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="adminname" value=<%=adminname %> id="userAction_save_do_logonName" class="bg"/>
					</td>
					
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						密码：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="password" value=<%=password %> id="userAction_save_do_logonName" class="bg"/>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						邮箱：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="email" value=<%=email %> id="userAction_save_do_logonName" class="bg"/>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						联系方式：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="telephone" value=<%=telephone %> id="userAction_save_do_logonName" class="bg"/>
					</td>
					
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						负责类别：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="cid" value=<%=cid %> id="userAction_save_do_logonName" class="bg"/>
					</td>
				</tr>
				
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="submit" id="userAction_save_do_submit" value="确定" class="button_ok">
							&#30830;&#23450;
						</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
						<span id="Label1"></span>
					</td>
				</tr>
			</table>
		</form>
	</body>
</HTML>