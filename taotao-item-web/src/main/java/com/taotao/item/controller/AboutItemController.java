package com.taotao.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.service.SearchAboutService;
import com.taotao.service.ItemParamService;
import com.taotao.utils.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by 蒋志鹏 on 2018/7/5.
 */

@Controller
public class AboutItemController {

    @Reference
    SearchAboutService searchAboutService;

    @Reference
    ItemParamService itemParamService;

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model) {
        System.out.println(itemId);
        TbItem item = searchAboutService.selectByPrimaryKey(itemId);
        model.addAttribute("item", item);
        return "item";
    }

    @RequestMapping(value="/item/desc/{itemId}", produces= MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemDesc(@PathVariable Long itemId) {
        String string = searchAboutService.getItemDescById(itemId);
        return string;
    }


    @RequestMapping("/item/param/{itemId}")
    @ResponseBody
    public String getItemParamItem(@PathVariable long itemId){
        TbItemParamItem tbItemParamItem = itemParamService.seItemParamItemById(itemId);
        if(tbItemParamItem!=null) {
            String paramdata = tbItemParamItem.getParamdata();
            List<Map> jsonList = JsonUtils.jsonToList(paramdata, Map.class);
            StringBuffer sb = new StringBuffer();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
            sb.append("    <tbody>\n");
            for(Map m1:jsonList) {
                sb.append("        <tr>\n");
                sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
                sb.append("        </tr>\n");
                List<Map> list2 = (List<Map>) m1.get("params");
                for(Map m2:list2) {
                    sb.append("        <tr>\n");
                    sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                    sb.append("            <td>"+m2.get("v")+"</td>\n");
                    sb.append("        </tr>\n");
                }
            }
            sb.append("    </tbody>\n");
            sb.append("</table>");
            return sb.toString();
        }
        return null;

    }

}
