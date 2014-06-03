<%@page import="com.dhtmlx.planner.controls.DHXAgendaView"%>
<%@page import="com.dhtmlx.planner.extensions.DHXExtension"%>
<html>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <!--
     <script>
         
         /* 
          * Switch to different modes based on the windows width.
          * this fucntion is not activatet yet.
          * arg null
          * return null
          * */
         function adjustWidth(){
             var _width = $(window).width(); 
             if( _width  < 500 ){
                 $('[name="day_tab"]').click();
             }else if( _width < 820 ){
                 $('[name="week_tab"]').click();
             }else{
                 $('[name="month_tab"]').click();
             }   
         }
         
          $(window).ready(adjustWidth);
          $(window).resize(adjustWidth);
         
     </script>
    -->

    <body>


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
                
                //adding readonly button
                s.views.add(new DHXAgendaView());//initializes the view
                s.extensions.add(DHXExtension.ICAL);
                s.toICal("../ical.jsp");
                //adding the readonly bib 
                s.extensions.add(DHXExtension.READONLY);
                
                s.config.setUpdateRender(true);
                
                s.load("assistanceEvents.jsp", DHXDataFormat.JSON);
                s.data.dataprocessor.setURL("assistanceEvents.jsp");
                return s.render();
            }
        %>
    </body>
</html>