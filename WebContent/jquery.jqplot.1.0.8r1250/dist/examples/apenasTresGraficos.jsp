<!DOCTYPE html>

<html>
<head>

    <title>Visao Executiva</title>


		<!-- APARENCIA -->
    <link class="include" rel="stylesheet" type="text/css" href="../jquery.jqplot.min.css" />
    <link rel="stylesheet" type="text/css" href="examples.min.css" />
    <link type="text/css" rel="stylesheet" href="syntaxhighlighter/styles/shCoreDefault.min.css" />
    <link type="text/css" rel="stylesheet" href="syntaxhighlighter/styles/shThemejqPlot.min.css" />
  
   
    <script class="include" type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    
   
</head>

<body>
  
    
        <div class="col1" id="example-content">

  
<!-- Example scripts go here -->


<div><span>Moused Over: </span><span id="info1b">Nothing</span></div>

<div id="chart1b" style="width:400px;height:260px;"></div>


<div id="chart1c" style="width:400px;height:260px;"></div>

<div id="chart2" style="width:600px;height:260px;"></div>


  


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
 
 
<script class="code" language="javascript" type="text/javascript">
$(document).ready(function(){   
    var l5 = [4, -3, 3, 6, 2, -2];
    var plot1c = $.jqplot('chart1c',[l5],{
       stackSeries: true,
       showMarker: false,
       seriesDefaults: {
           fill: true,
           fillToZero: true,
           rendererOptions: {
               highlightMouseDown: true
           }
       }
    });

    $('#chart1c').bind('jqplotDataClick', 
        function (ev, seriesIndex, pointIndex, data) {
            $('#info1c').html('series: '+seriesIndex+', point: '+pointIndex+', data: '+data);
        }
    );
});
</script>


<script class="code" language="javascript" type="text/javascript">
$(document).ready(function(){
    var l6 = [11, 9, 5, 12, 14, 8, 7, 9, 6, 11, 9, 3, 4];
    var l7 = [4, 8, 5, 3, 6, 5, 3, 2, 6, 7, 4, 3, 2];
    var l8 = [12, 6, 13, 11, 2, 3, 4, 2, 1, 5, 7, 4, 8];

    var ticks = [[1,'Dec 10'], [2,'Jan 11'], [3,'Feb 11'], [4,'Mar 11'], [5,'Apr 11'], [6,'May 11'], [7,'Jun 11'], [8,'Jul 11'], [9,'Aug 11'], [10,'Sep 11'], [11,'Oct 11'], [12,'Nov 11'], [13,'Dec 11']];  

    
    plot2 = $.jqplot('chart2',[l6, l7, l8],{
       stackSeries: true,
       showMarker: false,
       highlighter: {
        show: true,
        showTooltip: false
       },
       seriesDefaults: {
           fill: true,
       },
       series: [
        {label: 'Beans'},
        {label: 'Oranges'},
        {label: 'Crackers'}
       ],
       legend: {
        show: true,
        placement: 'outsideGrid'
       },
       grid: {
        drawBorder: false,
        shadow: false
       },
       axes: {
           xaxis: {
              ticks: ticks,
              tickRenderer: $.jqplot.CanvasAxisTickRenderer,
              tickOptions: {
                angle: -90 
              },
              drawMajorGridlines: false
          }           
        }
    });
    
    // capture the highlighters highlight event and show a custom tooltip.
    $('#chart2').bind('jqplotHighlighterHighlight', 
        function (ev, seriesIndex, pointIndex, data, plot) {
            // create some content for the tooltip.  Here we want the label of the tick,
            // which is not supplied to the highlighters standard tooltip.
            var content = plot.series[seriesIndex].label + ', ' + plot.series[seriesIndex]._xaxis.ticks[pointIndex][1] + ', ' + data[1];
            // get a handle on our custom tooltip element, which was previously created
            // and styled.  Be sure it is initiallly hidden!
            var elem = $('#customTooltipDiv');
            elem.html(content);
            // Figure out where to position the tooltip.
            var h = elem.outerHeight();
            var w = elem.outerWidth();
            var left = ev.pageX - w - 10;
            var top = ev.pageY - h - 10;
            // now stop any currently running animation, position the tooltip, and fade in.
            elem.stop(true, true).css({left:left, top:top}).fadeIn(200);
        }
    );
    
    // Hide the tooltip when unhighliting.
    $('#chart2').bind('jqplotHighlighterUnhighlight', 
        function (ev) {
            $('#customTooltipDiv').fadeOut(300);
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
