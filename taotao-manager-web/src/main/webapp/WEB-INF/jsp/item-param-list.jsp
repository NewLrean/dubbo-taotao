<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="easyui-datagrid" id="itemParamList" title="商品列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/item/param/list',method:'get',pageSize:20,toolbar:itemParamListToolbar">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'id',width:60">ID</th>
        	<th data-options="field:'itemCatId',width:80">商品类目ID</th>
        	<th data-options="field:'itemCatName',width:100">商品类目</th>
            <th data-options="field:'paramData',width:300,formatter:formatItemParamData">规格(只显示分组名称)</th>
            <th data-options="field:'created',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'updated',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>
        </tr>
    </thead>
</table>
<div id="itemEditWindow" class="easyui-window" title="编辑商品" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/rest/page/item-param-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>

	function formatItemParamData(value , index){
		var json = JSON.parse(value);
		var array = [];
		$.each(json,function(i,e){
			array.push(e.group);
		});
		return array.join(",");
	}

    function getSelectionsIds(){
    	var itemList = $("#itemParamList");
    	var sels = itemList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var itemParamListToolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	TAOTAO.createWindow({
        		url : "/item-param-add",
        	});
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){

            $.messager.alert('提示','该功能还未实现!');
            return;
            var ids = getSelectionsIds();
            if(ids.length == 0){
                $.messager.alert('提示','必须选择一个商品才能编辑!');
                return ;
            }
            if(ids.indexOf(',') > 0){
                $.messager.alert('提示','只能选择一个商品!');
                return ;
            }

            $("#itemEditWindow").window({
                onLoad :function(){
                    //回显数据
                    var data = $("#itemParamList").datagrid("getSelections")[0];
                    $("#itemParamAddTable").form("load",data);

                //加载商品规格
                    $.getJSON('/item/param/query/param/'+data.id,function(_data) {
                        if(_data.status==200&&_data.data){
                            var paramData = JSON.parse(_data.data.paramData);
                            var temple = $(".itemParamAddTemplate li").eq(0).clone();
                            for(var i in paramData) {
                                var pd = paramData[i];
                                //alert(pd.group);
                                $(".addGroup").click();
                                for(var j in pd.params) {
                                    var li = $(".itemParamAddTemplate li").eq(2).clone();
                                    li.appendTo($(this).parentsUntil("ul").parent());
                                    }

                            }
                        }
                    });
                }
            }).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中商品规格!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的商品规格吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/item/param/delete/"+ids, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','修改商品规格成功!',undefined,function(){
            					$("#itemParamList").datagrid("reload");
            				});
            			}else{
                            $.messager.alert('提示','修改商品规格失败!',undefined,function(){
                                $("#itemParamList").datagrid("reload");
                            });
                        }
            		});
        	    }
        	});
        }
    }];

    $(function () {
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
    })
</script>