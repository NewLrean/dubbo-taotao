<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="Nested Panel" data-options="width:'100%',minHeight:500,noheader:true,border:false" style="padding:10px;">
    <div class="easyui-layout" data-options="fit:true">
        <a id="import" href="javascript:void(0)">导入数据库文档</a>
    </div>
</div>
<script type="text/javascript">

    $("#import").on('click',function () {
        $.post("/solr/item/import", function(data){
            if(data.status == 200){
                $.messager.alert('提示','文档导入成功!');
            }else{
                $.messager.alert('提示','文档导入失败!');
            }
        });
    })

</script>