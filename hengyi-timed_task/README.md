# timed_task 定时任务

> 该模块无需添加EDAS shf.xml配置文件，因为该模块不提供服务也不消费服务。


## 该模块文件结构概览
com.hengyi.sapmapper—————————存放数据分析小组提供SAP Oracle数据库的mapper.xml
com.hengyi.mapper ——————————存放本项目Mysql数据库的mapper.xml

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run e2e tests
npm run e2e

# run all tests
npm test
```

For detailed explanation on how things work, checkout the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).
select * from 
(SELECT Budgetdetail.id,MaterialCostDetails.price from MaterialCostDetails  INNER JOIN Budgetdetail  where MaterialCostDetails.id=Budgetdetail.mate_pta) as a
LEFT JOIN
(SELECT Budgetdetail.id,MaterialCostDetails.price from MaterialCostDetails  INNER JOIN Budgetdetail  where MaterialCostDetails.id=Budgetdetail.mate_poy) as b on  a.id=b.id
LEFT JOIN
(SELECT Budgetdetail.id,MaterialCostDetails.price from MaterialCostDetails  INNER JOIN Budgetdetail  where MaterialCostDetails.id=Budgetdetail.mate_meg) as c on  b.id=c.id
