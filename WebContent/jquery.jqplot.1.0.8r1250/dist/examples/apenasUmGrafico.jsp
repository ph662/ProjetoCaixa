<!DOCTYPE html>

<html>
<head>

    <title>Um grafico</title>


		<!-- APARENCIA -->
    <link class="include" rel="stylesheet" type="text/css" href="../jquery.jqplot.min.css" />
    <link rel="stylesheet" type="text/css" href="examples.min.css" />
    <link type="text/css" rel="stylesheet" href="syntaxhighlighter/styles/shCoreDefault.min.css" />
    <link type="text/css" rel="stylesheet" href="syntaxhighlighter/styles/shThemejqPlot.min.css" />
  
   
 	<script type="text/javascript" src="jquery.js"></script>
    
   
</head>

<body>
  
    
        <div class="col1" id="example-content">

  
<!-- Example scripts go here -->


<div><span>Moused Over: </span><span id="info1b">Nothing</span></div>

<div id="chart1b" style="width:400px;height:260px;"></div>


<!--  div id="chart1c" style="width:400px;height:260px;"></div>

<div id="chart2" style="width:600px;height:260px;"></div -->


  


<script class="code" language="javascript" type="text/javascript">
$(document).ready(function(){

    var l2 = [11, 9, 5, 12, 14];
    var l3 = [4, 8, 5, 3, 6];
    var l4 = [12, 6, 13, 11, 2];    

    
    var plot1b = $.jqplot('chart1b',[l2, l3, l4],{
       stackSeries: true,
       showMarker: false,
       seriesDefaults: {
           fill: true
       },
       axes: {
           xaxis: {
               renderer: $.jqplot.CategoryAxisRenderer,
               ticks: ["Mon", "Tue", "Wed", "Thr", "Fri"]
           }
       }
    });
    
    $('#chart1b').bind('jqplotDataHighlight', 
        function (ev, seriesIndex, pointIndex, data) {
            $('#info1b').html('series: '+seriesIndex+', point: '+pointIndex+', data: '+data);
        }
    );
    
    $('#chart1b').bind('jqplotDataUnhighlight', 
        function (ev) {
            $('#info1b').html('Nothing');
        }
    );
});
</script>
 

<!-- End example scripts -->

<!-- Don't touch this! -->

    <script class="include" type="text/javascript" src="../jquery.jqplot.min.js"></script>
    <script type="text/javascript" src="syntaxhighlighter/scripts/shCore.min.js"></script>
    <script type="text/javascript" src="syntaxhighlighter/scripts/shBrushJScript.min.js"></script>
    <script type="text/javascript" src="syntaxhighlighter/scripts/shBrushXml.min.js"></script>
<!-- Additional plugins go here -->

  <script class="include" type="text/javascript" src="../plugins/jqplot.categoryAxisRenderer.min.js"></script>
  <script class="include" type="text/javascript" src="../plugins/jqplot.highlighter.min.js"></script>
  <script class="include" type="text/javascript" src="../plugins/jqplot.canvasTextRenderer.min.js"></script>
  <script class="include" type="text/javascript" src="../plugins/jqplot.canvasAxisTickRenderer.min.js"></script>

<!-- End additional plugins -->

        </div>
     
    
</body>


</html>
