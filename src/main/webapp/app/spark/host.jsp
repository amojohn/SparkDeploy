<%@page pageEncoding="utf-8"%>
<div class="tab-pane active" id="tab1">
	<br>
	<h3>
		<strong>Step 1 </strong> - 添加主机
	</h3>

	<div class="row">

		<div class="col-sm-12">
			<div class="form-group">
				<label>主机IP：</label>
				<textarea id="hostIp" name="hostIp" class="form-control" placeholder="" rows="3">172.16.219.[122-124]</textarea>
				<p class="note">说明：172.16.219.[122-124],172.16.219.128表示：172.16.219.122、172.16.219.123、172.16.219.124、172.16.219.128</p>
			</div>
			<div class="form-group">
				<label class="control-label">用户名：</label>
				<input id="spark_username" name="spark_username"
					class="form-control input-xs" value="root" readonly="readonly" type="text" />
			</div>
			<div class="form-group">
				<label class="control-label">密码：</label>
				<input id="spark_password" name="spark_password" value="hansight@2014"
					class="form-control input-xs" type="password" />
				<p class="note">说明：用来添加用户、设置SSH、开关防火墙、SELinux、安装配置</p>
			</div>
			
			<div class="form-group">
				<label class="control-label">SSH端口：</label>
				<input id="sshPort" name="sshPort" value="22"
					class="form-control input-xs" type=text" />
				<p class="note">说明：</p>
			</div>
		</div>

	</div>
</div>