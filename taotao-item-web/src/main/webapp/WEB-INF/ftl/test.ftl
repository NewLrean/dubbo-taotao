<html>

<body>
<#if tbitem.status=1>
商品id:${tbitem.id}
商品名称:${tbitem.title}
    <#include "test2.ftl">

<#else>
没有该商品介绍
</#if>



</body>
</html>