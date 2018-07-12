<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table cellpadding="5" style="margin-left: 30px" id="itemParamAddTable" class="itemParam">
	<tr>
		<td>商品类目:</td>
		<td>
			<input type="text" name="itemCatName" readonly style="border: 0">
			<input type="hidden" name="cid" style="width: 280px;"></input>
		</td>
	</tr>
	<tr class="addGroupTr">
		<td>规格参数:</td>
		<td>
			<ul>
				<li><a href="javascript:void(0)" class="easyui-linkbutton addGroup">添加分组</a></li>
			</ul>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<a href="javascript:void(0)" class="easyui-linkbutton submit">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton close">关闭</a>
		</td>
	</tr>
</table>
<div  class="itemParamAddTemplate" style="display: none;">
	<li class="param">
		<ul>
			<li>
				<input class="easyui-textbox" style="width: 150px;" name="group"/>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton addParam"  title="添加参数" data-options="plain:true,iconCls:'icon-add'"></a>
			</li>
			<li>
				<span>|-------</span><input  style="width: 150px;" class="easyui-textbox" name="param"/>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton delParam" title="删除" data-options="plain:true,iconCls:'icon-cancel'"></a>
			</li>
		</ul>
	</li>
</div>

<script type="text/javascript">
	$(function(){

		$("#itemParamAddTable .close").click(function(){
			$(".panel-tool-close").click();
		});

        $(".addGroup").click(function(){
            var temple = $(".itemParamAddTemplate li").eq(0).clone();
            $(this).parent().parent().append(temple);
            temple.find(".addParam").click(function(){
                var li = $(".itemParamAddTemplate li").eq(2).clone();
                li.find(".delParam").click(function(){
                    $(this).parent().remove();
                });
                li.appendTo($(this).parentsUntil("ul").parent());
            });
            temple.find(".delParam").click(function(){
                $(this).parent().remove();
            });
        });

		$("#itemParamAddTable .submit").click(function(){
			var params = [];
			var groups = $("#itemParamAddTable [name=group]");
			groups.each(function(i,e){
				var p = $(e).parentsUntil("ul").parent().find("[name=param]");
				var _ps = [];
				p.each(function(_i,_e){
					var _val = $(_e).siblings("input").val();
					if($.trim(_val).length>0){
						_ps.push(_val);
					}
				});
				var _val = $(e).siblings("input").val();
				if($.trim(_val).length>0 && _ps.length > 0){
					params.push({
						"group":_val,
						"params":_ps
					});
				}
			});
			var url = "/item/param/update"+$("#itemParamAddTable [name=cid]").val();
			$.post(url,{"paramData":JSON.stringify(params)},function(data){
				if(data.status == 200){
					$.messager.alert('提示','更新商品规格成功!',undefined,function(){
						$(".panel-tool-close").click();
                        $("#itemParamList").datagrid("reload");
    				});
				}
			});
		});
	});
</script>