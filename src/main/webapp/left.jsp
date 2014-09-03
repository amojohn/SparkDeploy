<%@ page pageEncoding="utf-8"%>
<aside id="left-panel">

	<!-- User info -->
	<div class="login-info">
		<span> <!-- User image size is adjusted inside CSS, it should stay as it --> 
			
			<a href="javascript:void(0);" id="show-shortcut">
				<img src="img/avatars/sunny.png" alt="me" class="online" /> 
				<span>
					宁弘道 
				</span>
				<i class="fa fa-angle-down"></i>
			</a> 
			
		</span>
	</div>
	<!-- end user info -->

	<!-- NAVIGATION : This navigation is also responsive

	To make this navigation dynamic please make sure to link the node
	(the reference to the nav > ul) after page load. Or the navigation
	will not initialize.
	-->
	<nav>
		<!-- NOTE: Notice the gaps after each icon usage <i></i>..
		Please note that these links work a bit different than
		traditional href="" links. See documentation for details.
		-->

		<ul>
			<li class="">
				<a href="ajax/dashboard.html" title="Dashboard"><i class="fa fa-lg fa-fw fa-home"></i> <span class="menu-item-parent">Dashboard</span></a>
			</li>
			<li>
				<a href="#"><i class="fa fa-lg fa-fw fa-bar-chart-o"></i> <span class="menu-item-parent">权限管理</span></a>
				<ul>
					<li>
						<a href="ajax/flot.html">用户</a>
					</li>
					<li>
						<a href="ajax/morris.html">角色</a>
					</li>
					<li>
						<a href="ajax/inline-charts.html">权限</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="#"><i class="fa fa-lg fa-fw fa-bar-chart-o"></i> <span class="menu-item-parent">运维平台</span></a>
				<ul>
					<li>
						<a href="app/spark.jsp">安装Spark</a>
					</li>
				</ul>
			</li>
		</ul>
	</nav>
	<span class="minifyme"> <i class="fa fa-arrow-circle-left hit"></i> </span>

</aside>