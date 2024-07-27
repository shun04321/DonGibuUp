<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%-- 상단 배너 --%>
<div class="top-banner col-lg-9">
	<div class="col-lg-6 col-md-6 col-12">
		<div class="custom-text-box d-flex flex-wrap d-lg-block mb-lg-0">
			<div class="counter-thumb">
				<div class="d-flex">
					<span class="counter-number" data-from="1" data-to="2009"
						data-speed="1000"></span> <span class="counter-number-text"></span>
				</div>

				<span class="counter-text">Founded</span>
			</div>

			<div class="counter-thumb mt-4">
				<div class="d-flex">
					<span class="counter-number" data-from="1" data-to="120"
						data-speed="1000"></span> <span class="counter-number-text">B</span>
				</div>

				<span class="counter-text">Donations</span>
			</div>
		</div>
	</div>
</div>

<%-- 박스 레이아웃 --%>
<div class="main-container col-lg-9">  
    <div class="d-flex justify-content-between">
        <div class="left-section mb-0">
            <section class="main-section" id="challenge1_section">
                <jsp:include page="/WEB-INF/views/main/fragment/challenge1.jsp" />
            </section>
            <section class="main-section" id="challenge2_section">
                 <jsp:include page="/WEB-INF/views/main/fragment/challenge2.jsp" />
            </section>
            <section class="main-section" id="subscription_section">
                 <jsp:include page="/WEB-INF/views/main/fragment/subscription.jsp" />
            </section>
        </div>
        <div class="right-section">
            <section class="main-section" id="dbox_section">
                <jsp:include page="/WEB-INF/views/main/fragment/dbox.jsp" />
            </section>
            <section class="main-section" id="goods_section">
                <jsp:include page="/WEB-INF/views/main/fragment/goods.jsp" />
            </section>
        </div>
    </div>
</div>

	<!-- JAVASCRIPT FILES -->
	<script src="${pageContext.request.contextPath}/t1/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/t1/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/t1/js/jquery.sticky.js"></script>
	<script src="${pageContext.request.contextPath}/t1/js/click-scroll.js"></script>
	<script src="${pageContext.request.contextPath}/t1/js/counter.js"></script>
	<script src="${pageContext.request.contextPath}/t1/js/custom.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
