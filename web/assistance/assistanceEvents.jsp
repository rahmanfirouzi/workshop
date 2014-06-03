<%@page import="view.AssistanceViewManager"%>
<%@ page contentType="application/json" %>
<%= getEvents(request)%>
<%@ page import="com.dhtmlx.planner.*,view.AssistanceViewManager.*" %>


<%!
    String getEvents(HttpServletRequest request) throws Exception {
    AssistanceViewManager evs = new AssistanceViewManager(request);
    
    
    
    
    return evs.run();
  }
    
   %> 
    

