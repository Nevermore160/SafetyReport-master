{
title: {
text: '受害者top5',
left: 'center',
align: 'right'
},
xAxis: {
type: 'category',
data: ${xaxis},
axisTick: {
   alignWithLabel: true
   },
    lineStyle: {
     type: "solid",
     color: "#fff", // 左边线的颜色
     width: "2" // 坐标线的宽度
    },
    axisLabel: {
      textStyle: {
      color: "#fff" // 坐标值得具体的颜色
    }
  }
},
yAxis: {
type: 'value'
},
series: [{
data: ${yaxis},
type: 'bar'
}]
}