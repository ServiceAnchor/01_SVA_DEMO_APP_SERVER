package com.sva.web.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.sva.model.AreaModel;
import com.sva.web.auth.AuthPassport;
import com.sva.web.models.CodeMngModel;
import com.sva.web.models.MsgMngModel;
import com.sva.web.models.SellerMngModel;

@Controller
@RequestMapping(value = "/home")
public class HomeController
{

    private static Logger log = Logger.getLogger(HomeController.class);

    @Autowired
    private LocaleResolver localeResolver;

    @AuthPassport
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model)
    {
        return "home/index";
    }

    @AuthPassport
    @RequestMapping(value = "/showStoreMng", method = {RequestMethod.GET})
    public String showSvaMng(Model model)
    {
        model.addAttribute("infoMng", true);
        model.addAttribute("storeMng", true);
        return "home/storeMng";
    }

    // 区域分类管理
    @AuthPassport
    @RequestMapping(value = "/showCategoryMng", method = {RequestMethod.GET})
    public String showCategoryMng(Model model)
    {
        model.addAttribute("infoMng", true);
        model.addAttribute("categoryMng", true);
        return "home/categoryMng";
    }

    
    @AuthPassport
    @RequestMapping(value = "/showSvaMng", method = {RequestMethod.GET})
    public String showSvaMng(Model model,
            @RequestParam(value = "info", required = false) String info)
    {
        model.addAttribute("infoMng", true);
        model.addAttribute("svaMng", true);
        model.addAttribute("info", info);
        return "home/svaMng";
    }

    @AuthPassport
    @RequestMapping(value = "/showMapMng", method = {RequestMethod.GET})
    public String showMapMng(Model model,
            @RequestParam(value = "info", required = false) String info)
    {
        model.addAttribute("mapMngModel", true);
        model.addAttribute("infoMng", true);
        model.addAttribute("mapMng", true);
        model.addAttribute("info", info);
        return "home/mapMng";
    }

    @AuthPassport
    @RequestMapping(value = "/showMsgMng", method = {RequestMethod.GET})
    public String showMsgMng(Model model,
            @RequestParam(value = "info", required = false) String info)
    {
        model.addAttribute("msgMngModel", new MsgMngModel());
        model.addAttribute("infoMng", true);
        model.addAttribute("msgMng", true);
        model.addAttribute("info", info);
        return "home/msgMng";
    }

    @AuthPassport
    @RequestMapping(value = "/showInputMng", method = {RequestMethod.GET})
    public String showInputMng(Model model)
    {
        model.addAttribute("msgInputModel", new AreaModel());
        model.addAttribute("infoMng", true);
        model.addAttribute("InputMng", true);
        return "home/inputMng";
    }

    @AuthPassport
    @RequestMapping(value = "/showSellerMng", method = {RequestMethod.GET})
    public String showSellerMng(Model model,
            @RequestParam(value = "info", required = false) String info)
    {
        model.addAttribute("sellerMngModel", new SellerMngModel());
        model.addAttribute("infoMng", true);
        model.addAttribute("sellerMng", true);
        model.addAttribute("info", info);
        return "home/sellerMng";
    }

    // 角色管理
    @AuthPassport
    @RequestMapping(value = "/role", method = {RequestMethod.GET})
    public String role(Model model)
    {
        model.addAttribute("infoMng", true);
        // model.addAttribute("tools", true);
        model.addAttribute("role", true);
        return "tool/role";
    }

    // 权限管理
    @AuthPassport
    @RequestMapping(value = "/account", method = {RequestMethod.GET})
    public String permissions(Model model)
    {
        model.addAttribute("infoMng", true);
        // model.addAttribute("role", true);
        model.addAttribute("account", true);
        return "tool/account";
    }

    @AuthPassport
    @RequestMapping(value = "/showPing", method = {RequestMethod.GET})
    public String showPing(Model model)
    {
        model.addAttribute("tools", true);
        model.addAttribute("ping", true);
        return "home/ping";
    }

    @AuthPassport
    @RequestMapping(value = "/showHeatmap", method = {RequestMethod.GET})
    public String showHeatmap(Model model)
    {
        model.addAttribute("customerStat", true);
        model.addAttribute("heatmap", true);
        return "home/heatmap";
    }

    @RequestMapping(value = "/sample/heatmap", method = {RequestMethod.GET})
    public String showHeatmapSample(Model model)
    {
        return "tool/heatmapSample";
    }

    @AuthPassport
    @RequestMapping(value = "/showHeatmap5", method = {RequestMethod.GET})
    public String showHeatmap5(Model model)
    {
        model.addAttribute("customerStat", true);
        model.addAttribute("heatmap5", true);
        return "home/heatmap5";
    }

    @AuthPassport
    @RequestMapping(value = "/showScattermap", method = {RequestMethod.GET})
    public String showScattermap(Model model)
    {
        model.addAttribute("customerStat", true);
        model.addAttribute("scattermap", true);
        return "home/scattermap";
    }

    @AuthPassport
    @RequestMapping(value = "/showLinemap", method = {RequestMethod.GET})
    public String showLinemap(Model model)
    {
        model.addAttribute("customerStat", true);
        model.addAttribute("linemap", true);
        return "home/linemap";
    }

    @AuthPassport
    @RequestMapping(value = "/showBarmap", method = {RequestMethod.GET})
    public String showBarmap(Model model)
    {
        model.addAttribute("customerStat", true);
        model.addAttribute("barmap", true);
        return "home/barmap";
    }

    @AuthPassport
    @RequestMapping(value = "/showRangemap", method = {RequestMethod.GET})
    public String showRangemap(Model model)
    {
        model.addAttribute("customerStat", true);
        model.addAttribute("rangemap", true);
        return "home/rangemap";
    }

    @AuthPassport
    @RequestMapping(value = "/showAreamap", method = {RequestMethod.GET})
    public String showAreamap(Model model)
    {
        model.addAttribute("customerStat", true);
        model.addAttribute("areamap", true);
        return "home/areamap";
    }

    @AuthPassport
    @RequestMapping(value = "/showCodeMng", method = {RequestMethod.GET})
    public String showCodeMng(Model model,
            @RequestParam(value = "info", required = false) String info)
    {
        model.addAttribute("codeMngModel", new CodeMngModel());
        model.addAttribute("locTest", true);
        model.addAttribute("codeMng", true);
        model.addAttribute("info", info);
        return "home/codeMng";
    }

    @AuthPassport
    @RequestMapping(value = "/showEstimate", method = {RequestMethod.GET})
    public String showEstimate(Model model,
            @RequestParam(value = "info", required = false) String info)
    {
        model.addAttribute("locTest", true);
        model.addAttribute("estimate", true);
        model.addAttribute("info", info);
        return "home/estimate";
    }

    @AuthPassport
    @RequestMapping(value = "/showAccuracy", method = {RequestMethod.GET})
    public String showAccuracy(Model model)
    {
        model.addAttribute("locTest", true);
        model.addAttribute("accuracyResult", true);
        return "home/accuracy";
    }

    @AuthPassport
    @RequestMapping(value = "/staticAccuracy", method = {RequestMethod.GET})
    public String staticAccuracy(Model model)
    {
        model.addAttribute("locTest", true);
        model.addAttribute("staticAccuracy", true);
        return "home/staticAccuracy";
    }

    @AuthPassport
    @RequestMapping(value = "/dynamicAccuracy", method = {RequestMethod.GET})
    public String dynamicAccuracy(Model model)
    {
        model.addAttribute("locTest", true);
        model.addAttribute("dynamicAccuracy", true);
        return "home/dynamicAccuracy";
    }

    @AuthPassport
    @RequestMapping(value = "/showLocationDelay", method = {RequestMethod.GET})
    public String showLocationdelay(Model model)
    {
        model.addAttribute("locTest", true);
        model.addAttribute("locationdelay", true);
        return "home/locationdelay";
    }

    @AuthPassport
    @RequestMapping(value = "/showPhone", method = {RequestMethod.GET})
    public String showPhone(Model model)
    {
        model.addAttribute("locTest", true);
        model.addAttribute("phone", true);
        return "home/phone";
    }

    @AuthPassport
    @RequestMapping(value = "/showMessagePush", method = {RequestMethod.GET})
    public String showMessagepush(Model model)
    {
        model.addAttribute("locTest", true);
        model.addAttribute("messagepush", true);
        return "home/messagepush";
    }

    @AuthPassport
    @RequestMapping(value = "/showBluemix", method = {RequestMethod.GET})
    public String showBluemix(Model model)
    {
        model.addAttribute("tools", true);
        model.addAttribute("bluemix", true);
        return "tool/bluemix";
    }

    @AuthPassport
    @RequestMapping(value = "/showpRRU", method = {RequestMethod.GET})
    public String showpRRU(Model model,
            @RequestParam(value = "info", required = false) String info)
    {
        model.addAttribute("tools", true);
        model.addAttribute("pRRU", true);
        model.addAttribute("info", info);
        return "tool/pRRU";
    }

    // 参数配置
    @AuthPassport
    @RequestMapping(value = "/showparam", method = {RequestMethod.GET})
    public String showparam(Model model)
    {
        model.addAttribute("tools", true);
        model.addAttribute("paramconfig", true);
        return "tool/paramconfig";
    }

    @AuthPassport
    @RequestMapping(value = "/showDown", method = {RequestMethod.GET})
    public String showDown(Model model)
    {
        model.addAttribute("tools", true);
        model.addAttribute("down", true);
        return "tool/down";
    }

    @AuthPassport
    @RequestMapping(value = "/uploadQRcode", method = {RequestMethod.GET})
    public String uploadQRcode(Model model)
    {
        model.addAttribute("tools", true);
        model.addAttribute("qrcode", true);
        return "tool/qrcode";
    }

    @AuthPassport
    @RequestMapping(value = "/showFollow", method = {RequestMethod.GET})
    public String showFollow(Model model)
    {
        model.addAttribute("customerStat", true);
        model.addAttribute("follow", true);
        return "home/follow";
    }

    @AuthPassport
    @RequestMapping(value = "/showOverView", method = {RequestMethod.GET})
    public String showOverView(Model model)
    {
        model.addAttribute("showOverView", true);
        model.addAttribute("OverView", true);
        return "home/overView";
    }
    
    @AuthPassport
    @RequestMapping(value = "/showElectronic", method = {RequestMethod.GET})
    public String showElectronic(Model model)
    {
        model.addAttribute("infoMng", true);
        model.addAttribute("Electronic", true);
        return "home/electronic";
    }
    //参数管理
    @AuthPassport
    @RequestMapping(value = "/content", method = {RequestMethod.GET})
    public String Contentshow (Model model)
    {
    model.addAttribute("content", true);
    model.addAttribute("infoMng", true);
    model.addAttribute("contentshow", true);
    return "home/contentshow";
    }
    
    @RequestMapping(value = "/changeLocal", method = {RequestMethod.GET})
    public String changeLocal(HttpServletRequest request, String local,
            HttpServletResponse response)
    {
        if ("zh".equals(local))
        {
            localeResolver.setLocale(request, response, Locale.CHINA);
        }
        else if ("en".equals(local))
        {
            localeResolver.setLocale(request, response, Locale.ENGLISH);
        }
        // request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
        // Locale.ENGLISH);
        // String backUrl = request.getRequestURI();
        String lastUrl = request.getHeader("Referer");
        String str = null;
        if (lastUrl.indexOf("?") != -1)
        {
            str = lastUrl.substring(0, lastUrl.lastIndexOf("?"));
        }
        else
        {
            str = lastUrl;
        }
        RequestContext requestContext = new RequestContext(request);

        Locale myLocale = requestContext.getLocale();

        log.info(myLocale);

        return "redirect:" + str;
    }

    @RequestMapping(value = "/notfound")
    public ModelAndView notfound()
    {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("404");

        return mv;
    }
}
