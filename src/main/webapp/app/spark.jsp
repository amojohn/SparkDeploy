<%@page pageEncoding="utf-8" %>
<div class="row">
	<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
		<h1 class="page-title txt-color-blueDark"><i class="fa fa-pencil-square-o fa-fw "></i> 运维平台 <span>>
			安装Spark </span></h1>
	</div>
</div>

<!-- widget grid -->
<section id="widget-grid" class="">

	<!-- row -->
	<div class="row">

		<!-- NEW WIDGET START -->
		<article class="col-sm-12 col-md-12 col-lg-12">

			<!-- Widget ID (each widget will need unique ID)-->
			<div class="jarviswidget" id="wid-id-0"
				data-widget-editbutton="false"
				data-widget-colorbutton="false"
				data-widget-deletebutton="false"
				data-widget-togglebutton="false"
				data-widget-fullscreenbutton="false"
				data-widget-sortable="false">
				<header>
					<span class="widget-icon"> <i class="fa fa-check"></i> </span>
					<h2>安装步骤 </h2>
				</header>

				<!-- widget div-->
				<div>
					<!-- widget content -->
					<div class="widget-body">
						<div class="row">
							<form id="wizard-1" novalidate="novalidate">
								<div id="bootstrap-wizard-1" class="col-sm-12">
									<%@include file="./spark/form-bootstrap-wizard.jsp" %>
									<div class="tab-content">
										<%@include file="./spark/host.jsp" %>
										<%@include file="./spark/user.jsp" %>
										<%@include file="./spark/role.jsp" %>
										<%@include file="./spark/config.jsp" %>
										<%@include file="./spark/review.jsp" %>
										<%@include file="./spark/result.jsp" %>
										<div class="form-actions">
											<div class="row">
												<div class="col-sm-12">
													<ul class="pager wizard no-margin">
														<!--<li class="previous first disabled">
														<a href="javascript:void(0);" class="btn btn-lg btn-default"> First </a>
														</li>-->
														<li class="previous disabled">
															<a href="javascript:void(0);" class="btn btn-lg btn-default"> Previous </a>
														</li>
														<!--<li class="next last">
														<a href="javascript:void(0);" class="btn btn-lg btn-primary"> Last </a>
														</li>-->
														<li class="next">
															<a href="javascript:void(0);" class="btn btn-lg txt-color-darken"> Next </a>
														</li>
													</ul>
												</div>
											</div>
										</div>

									</div>
								</div>
							</form>
						</div>

					</div>
					<!-- end widget content -->

				</div>
				<!-- end widget div -->

			</div>
			<!-- end widget -->

		</article>
		<!-- WIDGET END -->
	</div>
		<!-- Modal -->
<div id="step6_modal" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">部署Spark</h4>
			</div>
			<div class="modal-body">
				……		
			</div>
			<div class="modal-footer">
				<button type="button" disabled="disabled" class="btn btn-primary">
					完成
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</section>
<!-- end widget grid -->

<script type="text/javascript">
	// DO NOT REMOVE : GLOBAL FUNCTIONS!
	pageSetUp();

	// PAGE RELATED SCRIPTS

	/*
	 * Load bootstrap wizard dependency
	 */
	loadScript("js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.min.js", runBootstrapWizard);
	
	//Bootstrap Wizard Validations
	
	function runBootstrapWizard() {
	  var $validator = $("#wizard-1").validate({
	    rules: {
	    	hostIp: {
	        	required: true//TODO validate
	      	},
	      	hostName: {
	        	required: true
	      	},
	      	password: {
	        	required: true
	      	},
	      	master:{required: true }
	    },
	    messages: {
	    	hostIp: "主机IP不能为空",
	    	hostName:"主机名称不能为空",
	    	password:"密码不能为空",
	    	master:"必须选择主节点"
	    },
	    
	    highlight: function (element) {
	      $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
	    },
	    unhighlight: function (element) {
	      $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
	    },
	    errorElement: 'span',
	    errorClass: 'help-block',
	    errorPlacement: function (error, element) {
	      if (element.parent('.input-group').length) {
	        error.insertAfter(element.parent());
	      } else {
	        error.insertAfter(element);
	      }
	    }
	  });
	  var allHostIp = [];
	  $('#bootstrap-wizard-1').bootstrapWizard({
	    'tabClass': 'form-wizard',
	    'onNext': function (tab, navigation, index) {
	      var $valid = $("#wizard-1").valid();
	      if (index == 1) {
	    	  $.post('rest/deploy/spark/check', {ips:$("#hostIp").val(),
	    		  username:$("#spark_username").val(),
	    		  password:$("#spark_password").val(),
	    		  sshPort: $("#sshPort").val()
	    		  }, function(data){
	    			  console.log(data.result)
	    		  if (data.result != "SUCCESS") {
	    			  res(false);
	    		  } else {
	    			  res($valid);
		    		  allHostIp = data.hosts;
		    		  console.log(data);
	    		  }
	    	  });
	      } else if (index == 2) {
	    	  $("#step3_master option").remove()
	    	  var opts = "";
	    	  for (var i=0; i<allHostIp.length; i++) {
	    		  opts += "<option value=\""+allHostIp[i]+"\">"+allHostIp[i]+"</option>";
	    	  }
	    	  $("#step3_master").append(opts);
	      } else if (index == 3) {
	    	  $("#SPARK_MASTER_IP").val($("#step3_master").val());
	      } else if (index == 4) {
	    	  $("#step3_content :text").each(function(){
	    		  var id = $(this).attr("name"), val = $(this).val();
	    		  $("#review_" + id).html(val);
	    	  });
	      } else if (index == 5) {
	    	  $('#step6_modal').modal('show');
	    	  var data = {hostIp:$("#hostIp").val(),
		    		  username:$("#spark_username").val(),
		    		  password:$("#spark_password").val(),
		    		  sshPort: $("#sshPort").val(),
		    		  sparkUser:$("#sparkUser").val(),
		    		  step3_master:$("#step3_master").val(),
		    		  hostName:$("#hostName").val()
		      };
	    	  $("#step3_content :text").each(function(){
	    		  var id = $(this).attr("name"), val = $(this).val();
	    		  data[id]=val;
	    	  });
	    	  console.log(data)
	    	  $.post("rest/deploy/spark/install", data, function(data){
	    		  if (data.result == "SUCCESS") {
	    			  res(true);
	    		  }
	    	  });
	      } else {
	    	  res($valid);
	      }
	      
		  function res(valid) {
			  if (!valid) {
		        $validator.focusInvalid();
		        return false;
		      } else {
		        $('#bootstrap-wizard-1').find('.form-wizard').children('li').eq(index - 1).addClass(
		          'complete');
		        $('#bootstrap-wizard-1').find('.form-wizard').children('li').eq(index - 1).find('.step')
		        .html('<i class="fa fa-check"></i>');
		      }
		  }
	    }
	  });
	  
	}
	
	/*
	 * Load fuelux wizard dependency
	 */
	loadScript("js/plugin/fuelux/wizard/wizard.js", fueluxWizard);
	
	function fueluxWizard() {
	  
	  var wizard = $('.wizard').wizard();
	  
	  wizard.on('finished', function (e, data) {
	    //$("#fuelux-wizard").submit();
	    //console.log("submitted!");
	    $.smallBox({
	      title: "Congratulations! Your form was submitted",
	      content: "<i class='fa fa-clock-o'></i> <i>1 seconds ago...</i>",
	      color: "#5F895F",
	      iconSmall: "fa fa-check bounce animated",
	      timeout: 4000
	    });
	    
	  });
	  
	}

</script>
