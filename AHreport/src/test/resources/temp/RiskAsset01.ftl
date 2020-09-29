{
title: {
text: '资产分布饼图',
left: 'center',
align: 'right'
},
tooltip: {
trigger: 'item',
formatter: '{a} <br/>{b}: {c} ({d}%)'
},
legend: {
orient: 'vertical',
left: 10,
data: ['低风险', '高风险', '已失陷']
},
series: [
{
name: '访问来源',
type: 'pie',
radius: ['50%', '70%'],
avoidLabelOverlap: false,
label: {
show: false,
position: 'center'
},
emphasis: {
label: {
show: true,
fontSize: '30',
fontWeight: 'bold'
}
},
labelLine: {
show: false
},
data: ${list}
}
]
}