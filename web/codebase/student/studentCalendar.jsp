

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
        s.config.setTouchDrag(500);
        s.extensions.add(DHXExtension.READONLY);
        s.load("studentEvents.jsp", DHXDataFormat.JSON);
        s.data.dataprocessor.setURL("studentEvents.jsp");
        return s.render();
    }
%>
