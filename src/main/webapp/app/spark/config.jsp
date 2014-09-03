<%@page pageEncoding="utf-8"%>
<div class="tab-pane" id="tab4">
	<br>
	<h3>
		<strong>Step 4 </strong> - 配置
	</h3>
	<div class="row">
		<div class="col-sm-12" id="step3_content">
			<h3>Master</h3>
			<div class="form-group">
				<label class="control-label">SPARK_MASTER_IP：</label> 
				<input id="SPARK_MASTER_IP" name="SPARK_MASTER_IP" class="form-control input-xs" 
					type="text" readonly="readonly" />
				<p class="note">说明：master绑定的IP或主机名。</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_MASTER_PORT：</label> 
				<input name="SPARK_MASTER_PORT" value="7077" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：默认7077。</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_MASTER_WEBUI_PORT：</label> 
				<input name="SPARK_MASTER_WEBUI_PORT" value="8080" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：默认8080。</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_MASTER_OPTS：</label> 
				<input name="SPARK_MASTER_IP" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：设置master的配置属性，例如 "-Dx=y"。</p>
			</div>
			<h3>Master & Worker</h3>
			<div class="form-group">
				<label class="control-label">SPARK_DAEMON_JAVA_OPTS：</label> 
				<input name="SPARK_MASTER_IP" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：设置所有进程的配置属性，例如 "-Dx=y"。</p>
			</div>
			
			<h3>Worker</h3>
			<div class="form-group">
				<label class="control-label">SPARK_WORKER_CORES：</label> 
				<input name="SPARK_WORKER_CORES" value="" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：设置使用Worker主机的CPU核数，不设则使用所有。用来为主机的其它应用预留CPU核</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_WORKER_MEMORY：</label> 
				<input name="SPARK_MASTER_IP" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：设置worker可以提供给executor的内存数 (如 1000m, 2g)。</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_WORKER_PORT：</label> 
				<input name="SPARK_MASTER_IP"  class="form-control input-xs" 
					type="text" />
				<p class="note">说明：默认随机。</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_WORKER_WEBUI_PORT：</label> 
				<input name="SPARK_MASTER_IP" value="8081" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：默认8081。</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_WORKER_INSTANCES：</label> 
				<input name="SPARK_MASTER_IP" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：设置一个节点可以运行多少worker(屌丝专属，伪分布式)。</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_WORKER_DIR：</label> 
				<input name="SPARK_MASTER_IP" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：worker进程的工作目录。</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_WORKER_OPTS：</label> 
				<input name="SPARK_MASTER_IP" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：设置worker的配置属性，例如 ："-Dx=y"。</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_HISTORY_OPTS：</label> 
				<input name="SPARK_MASTER_IP" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：设置history server的配置属性，例如 "-Dx=y"。</p>
			</div>
			<div class="form-group">
				<label class="control-label">SPARK_PUBLIC_DNS：</label> 
				<input name="SPARK_MASTER_IP" class="form-control input-xs" 
					type="text" />
				<p class="note">说明：设置master和worker共用的DNS。</p>
			</div>
		</div>
	</div>
</div>