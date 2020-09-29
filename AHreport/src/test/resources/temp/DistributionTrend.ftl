{
title: {
text: '数据时序分布趋势柱状图'
},
tooltip: {
trigger: 'axis',
axisPointer: {
type: 'cross',
label: {
backgroundColor: '#6a7985'
}
}
},
legend: {
data: ['低风险', '中风险', '高风险']
},
toolbox: {
feature: {
saveAsImage: {}
}
},
grid: {
left: '3%',
right: '4%',
bottom: '3%',
containLabel: true
},
xAxis: [
{
type: 'category',
boundaryGap: false,
data: ${list}
}
],
yAxis: [
{
type: 'value'
}
],
series: [
{
name: '低风险',
type: 'line',
stack: '总量',
areaStyle: {},
data: ${low}
},
{
name: '中风险',
type: 'line',
stack: '总量',
areaStyle: {},
data: ${medium}
},
{
name: '高风险',
type: 'line',
stack: '总量',
areaStyle: {},
data: ${high}
}
]
}