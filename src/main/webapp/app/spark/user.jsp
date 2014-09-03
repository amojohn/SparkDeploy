<%@page pageEncoding="utf-8"%>
<div class="tab-pane" id="tab2">
	<br>
	<h3>
		<strong>Step 2 </strong> - 添加用户与设置主机名
	</h3>

	<div class="row">
		<div class="col-sm-12">
			<div class="form-group">
				<label class="control-label">主机名称：</label>
				<input id="hostName" name="hostName"
					class="form-control input-xs" value="hdp" type="text" />
				<p class="note">说明：可选，如果非空则设置主机名。规则为此处所填名称+IP可区分后缀。</p>
			</div>
			<div class="form-group">
				<label class="control-label">运行Spark的用户：</label>
				<input id="sparkUser" name="sparkUser" value="spark"
					class="form-control input-xs" type="text" />
			</div>
		</div>

	</div>
</div>