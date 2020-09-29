{
title: {
text: '攻击链Top5',
left: 'center',
align: 'right'
},
tooltip: {
trigger: 'axis',
axisPointer: {            // 坐标轴指示器，坐标轴触发有效
type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
}
},

grid: {
left: '3%',
right: '4%',
bottom: '3%',
containLabel: true
},
xAxis: {
type: 'value'
},
yAxis: {
type: 'category',
data: ${titleName}
},
series: [
{
name: '搜索引擎',
type: 'bar',
stack: '总量',
label: {
show: true,
position: 'insideRight'
},
data: ${data}
}
]
}