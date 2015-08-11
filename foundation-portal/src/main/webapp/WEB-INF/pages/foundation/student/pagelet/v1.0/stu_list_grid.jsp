<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglibs.jsp"%>
<%@taglib prefix="nsp" uri="/WEB-INF/tag/portal.tld" %>
<table class="standard_table th-bg standard_table_tt">
	<tbody>
		<tr>
			<th class="th-1"><input title="全选/不选" type="checkbox"
				class="check_box" onclick="selectAll(this)"></th>
			<th class="th-3">学号</th>
			<th class="th-4">班级</th>
			<th class="th-5">性别</th>
			<th class="th-6">姓名</th>
			<th class="th-7">出生日期</th>

		</tr>
	</tbody>
</table>
<table class="standard_table standard_table_con">
	<tbody>
		<c:forEach var="stu" items="${pageView.records}">
			<tr>
				<td class="td-1"><input type="checkbox" name="menuids"
					value="${stu.ID}" class="check_box"></td>
				<td class="td-3">${stu.NO}</td>
				<td class="td-4">${stu.CLASSNAME}</td>
				<td class="td-5">${stu.SEX}</td>
				<td class="td-6">${stu.NAME}</td>
				<td class="td-7">
					<fmt:formatDate pattern="yyyy-MM-dd" value="${stu.BIRTHDAY}" type="both"/>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<table class="page-list">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0"
				cellpadding="0" class="fenye_table">
				<tr>
					<td style="width:10px;"></td>
				</tr>
				<tr>
					<td style="width:10px;"></td>
					<nsp:paging pageView="${pageView}"/>
				</tr>
			</table>
		</td>
	</tr>
</table>