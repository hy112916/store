<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜单</title>
<link href="${pageContext.request.contextPath}/css/left.css" rel="stylesheet" type="text/css"/>
<link rel="StyleSheet" href="${pageContext.request.contextPath}/css/dtree.css" type="text/css" />
</head>
<body>
<table width="100" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="12"></td>
  </tr>
</table>
<table width="100%" border="0">
  <tr>
    <td>
<div class="dtree">

	<a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dtree.js"></script>
	<script type="text/javascript">
	
		d = new dTree('d');
		d.add('01',-1,'系统菜单树');
		d.add('0102','01','分类管理','','','mainFrame');
		d.add('010201','0102','分类列表','${pageContext.request.contextPath}/adminCategory?method=findAll','','mainFrame');
		d.add('010202','0102','添加分类','${pageContext.request.contextPath}/adminCategory?method=addUI','','mainFrame');
		d.add('0103','01','销售员管理');
		d.add('010301','0103','人员列表','${pageContext.request.contextPath}/admin?method=findAll','','mainFrame');
		d.add('010302','0103','添加人员','${pageContext.request.contextPath}/admin?method=addUI','','mainFrame');
		d.add('0104','01','商品管理');
		d.add('010401','0104','已上架商品列表','${pageContext.request.contextPath}/adminProduct?method=findAll','','mainFrame');
		d.add('010402','0104','添加商品','${pageContext.request.contextPath}/adminProduct?method=addUI','','mainFrame');
		d.add('0105','01','订单管理');
		d.add('010501','0105','订单列表','${pageContext.request.contextPath}/adminOrder?method=findAllByState','','mainFrame');
		d.add('010502','0105','未付款订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=0','','mainFrame');
		d.add('010503','0105','已付款订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=1','','mainFrame');
		d.add('010504','0105','已发货订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=2','','mainFrame');
		d.add('010505','0105','已完成订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=3','','mainFrame');
		d.add('0106','01','统计管理');
		d.add('010601','0106','商品报表','${pageContext.request.contextPath}/adminProduct?method=searchUI','','mainFrame');
		d.add('010602','0106','总商品报表','${pageContext.request.contextPath}/adminProduct?method=statis','','mainFrame');
		d.add('0107','01','大数据');
		d.add('010701','0107','用户画像','${pageContext.request.contextPath}/statis?method=userPortrait','','mainFrame');
		d.add('010702','0107','销售预测','${pageContext.request.contextPath}/statis?method=salesPredict','','mainFrame');
		d.add('010703','0107','销售异常','${pageContext.request.contextPath}/statis?method=salesNotice','','mainFrame');
		document.write(d);
		
	</script>
</div>	</td>
  </tr>
</table>
</body>
</html>
