<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
		<script language="javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
		<script type="text/javascript">
			function showDetail(oid){
				//alert(oid);
				//发送ajax
				$.post("${pageContext.request.contextPath}/adminOrder",{"method":"showDetail","oid":oid},function(d){
					//alert(d);
					var s="<table border='1' width='99%'>";
					s+="<tr><th>商品名称</th><th>购买数量</th></tr>"
					
					$(d).each(function(){
						s+="<tr><td>"+this.product.pname+"</td><td>"+this.count+"</td></tr>";
					});
					
					s+="</table>";
					
					layer.open({
				        type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
				        title:"订单号:"+oid,//标题
				        area: ['520px', '300px'],//大小
				        shadeClose: true, //点击弹层外区域 遮罩关闭
				        content: s//内容
				    });
				},"json");
			}
		</script>
	</HEAD>
	<body>
		<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</TD>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="10%">
										序号
									</td>
									<td align="center" width="10%">
										订单编号
									</td>
									<td align="center" width="10%">
										订单金额
									</td>
									<td align="center" width="10%">
										收货人
									</td>
									<td align="center" width="10%">
										订单状态
									</td>
									<td align="center" width="50%">
										订单详情
									</td>
								</tr>
									<c:forEach items="${list }" var="o" varStatus="vs">
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="18%">
												${vs.count }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${o.oid }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${o.total }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${o.name }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												<c:if test="${o.state == 0 }">未付款</c:if>
												<c:if test="${o.state == 1 }">
													<a href="${pageContext.request.contextPath }/adminOrder?method=updateState&oid=${o.oid}">去发货</a>
												</c:if>
												<c:if test="${o.state == 2 }">待收货</c:if>
												<c:if test="${o.state == 3 }">已完成</c:if>
											</td>
											<td align="center" style="HEIGHT: 22px">
												<input type="button" value="订单详情"  onclick="showDetail('${o.oid}')"/>
											</td>
							
										</tr>
									</c:forEach>
							</table>
						</td>
					</tr>
					
				</TBODY>
			</table>
		</form>
	</body>
</HTML>

