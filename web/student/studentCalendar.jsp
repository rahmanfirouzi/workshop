

<%@page import="com.dhtmlx.planner.controls.DHXAgendaView"%>
<%@page import="com.dhtmlx.planner.extensions.DHXExtension"%>
<%@page import="java.util.Map"%>
<%@page import="javax.faces.context.FacesContext"%>
<div class="planner" id="planner"><%= getPlanner(request)%></div>
<%@ page import="com.dhtmlx.planner.*,com.dhtmlx.planner.data.*" %>
<%!
    String getPlanner(HttpServletRequest request) throws Exception {

        DHXPlanner s = new DHXPlanner("../codebase/", DHXSkin.TERRACE);

        s.setWidth(100, "%");
        s.setHeight(100, "%");
        s.config.setTouch(true);
        s.config.setFullDay(false);
        s.config.setTouchDrag(1000);
        s.config.setScrollHour(8);
        s.config.setTouchTip(true);
        s.views.add(new DHXAgendaView());//initializes the view
        s.extensions.add(DHXExtension.ICAL);
        s.toICal("../ical.jsp");
        s.extensions.add(DHXExtension.READONLY);
        
        s.config.setUpdateRender(true);


        
        
        s.load("studentEvents.jsp", DHXDataFormat.JSON);
        s.data.dataprocessor.setURL("studentEvents.jsp");
        return s.render();
    }
%>
