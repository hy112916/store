<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
	<%@include file="/jsp/head.jsp" %>


		<div class="container">
			<div class="row">

				<div style="margin:0 auto;margin-top:10px;width:950px;">
					<strong>订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th colspan="2">订单编号:${bean.oid } </th>
								<th colspan="1">
									<c:if test="${bean.state == 0 }">去付款</c:if>
									<c:if test="${bean.state == 1 }">已付款</c:if>
									<c:if test="${bean.state == 2 }">确认收货</c:if>
									<c:if test="${bean.state == 3 }">已完成</c:if>
								</th>
								<th colspan="2">时间:<fmt:formatDate value="${bean.ordertime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								 </th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach items="${bean.items }" var="oi">
								<tr class="active">
									<td width="60" width="40%">
										<input type="hidden" name="id" value="22">
										<img src="${pageContext.request.contextPath}/${oi.product.pimage}" width="70" height="60">
									</td>
									<td width="30%">
										<a target="_blank">${oi.product.pname}</a>
									</td>
									<td width="20%">
										￥${oi.product.shop_price}
									</td>
									<td width="10%">
										${oi.count}
									</td>
									<td width="15%">
										<span class="subtotal">￥${oi.subtotal}</span>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div style="text-align:right;margin-right:120px;">
					商品金额: <strong style="color:#ff6600;">￥${bean.total }元</strong>
				</div>

			</div>

			<div>
				<hr/>
				<form action="${pageContext.request.contextPath }/order" id="orderForm" method="post" class="form-horizontal" style="margin-top:5px;margin-left:150px;">
					<!-- 提交的方法 -->
					<input type="hidden" name="method" value="pay">
					<!-- 订单号 -->
					<input type="hidden" name="oid" value="${bean.oid }">
					<div class="form-group">
						<label for="address" class="col-sm-1 control-label">地址</label>
						<div class="col-sm-5">
							<input type="text" name="address" class="form-control"  placeholder="请输入收货地址">
						</div>
					</div>
					<div class="form-group">
						<label for="receiver" class="col-sm-1 control-label">收货人</label>
						<div class="col-sm-5">
							<input type="text" name="name" class="form-control"  placeholder="请输收货人">
						</div>
					</div>
					<div class="form-group">
						<label for="telephone" class="col-sm-1 control-label">电话</label>
						<div class="col-sm-5">
							<input type="text" name="telephone" class="form-control"  placeholder="请输入联系方式">
						</div>
					</div>
					<div class="form-group">
						<strong>请在下方输入你的会员密码：</strong>
						<hr/>
						<div class="col-sm-5">
							<input type="password" class="form-control" id="inputPassword" placeholder="请输入密码" name="password">
    				
						</div>
					</div>
				

				<hr/>
				<div style="margin-top:5px;margin-left:150px;">
					<p style="text-align:right;margin-right:100px;">
						<a href="javascript:document.getElementById('orderForm').submit();">
							<img src="${pageContext.request.contextPath}/images/finalbutton.gif" width="204" height="51" border="0" />
						</a>
					</p>
					<hr/>

				</div>
			</div>
				</form>
		</div>

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a href="https://github.com/hy112916/store">Github项目</a></li>
				<li><a href="https://www.csdn.net/">CSDN</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2019 颍君建安 版权所有
		</div>

	</body>

</html>