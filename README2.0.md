# **中山大学数据科学与计算机学院本科生实验报告**
（2019年秋季学期）
	课程名称：区块链原理与技术						任课教师： 郑子彬
	
| 年级 | 2017|  专业（方向）| 软件工程|
|--|--|--|--|
|  学号| 17343160 |姓名|赵孟阳
|电话|13242886623|Email|1442196038@qq.com
|开始日期|2019.11.14|完成日期|2019.12.13	

## 一、	项目背景
* 本项目是区块链+供应链金融的一个方案，针对传统供应链金融中核心企业的信用无法在整个供应链中传递以及交易信息不透明化的缺点，进行新的方案设计。
* 在本方案中，实现了将供应链上的每一笔交易和应收账款单据上链，同时引入第三方可信机构来确认这些信息的交易，例如银行，物流公司等，确保交易和单据的真实性。同时，支持应收账款的转让，融资，清算等，让核心企业的信用可以传递到供应链的下游企业，减小中小企业的融资难度。
 
## 二、	方案设计
1） 存储设计：
使用结构体Bill的可变长度数组保存账单，Bill结构体中包括了账单涉及双方的地址，金额，合约自动支付时间，合约是否已经结束。其中账单的欠款方在进行账单转移后仍记录为原来的账单的欠款公司，从而使核心企业的信用可以传递，方便中小企业贷款。
2） 数据流图：
![数据流图](https://img-blog.csdnimg.cn/20191214113220876.png)
3）  核心功能介绍：
 1. 银行监管：每个账户交易的发出方为自身，所以可以在账户交易前进行授权，保证交易的发出经过银行验证，交易发出方有效。
```
struct Trader{
		bool isPermitted;//在交易者发生购买或转让订单前必须先由银行进行授权
	}
	//账单的签订或转移需要银行给出权限
	function giveRightToTrader(address trader) private{
		require (msg.sender == bank,
			"Only bank can give right to trade"
		);
		require (traders[trader].isPermitted == false);
		traders[trader].isPermitted = true;
	}
```
2.  购买商品并签订账单（purchaseAndMakeBill）:操作者作为买家，与seller签订账单，经过lastTimeh后自动归还，并将交易event保存
```
function purchaseAndMakeBill(address _seller,uint _value,uint _lastTime)public//购买物品，签订账单
    {
        traders[msg.sender].isPermitted = true;
		require(
			traders[msg.sender].isPermitted == true,
			"Only getting right from bank can trade"
		);
        require(_seller != msg.sender, "Self-trader is disallowed.");
		require(_seller != address(0) && _value >= 0,"Not valid address or value");
		require(_lastTime > 0, "The lastTime need to bigger than zero");
       
		//签订账单
        Bills.push(Bill({
			value : _value,
			billEndTime : (now + _lastTime),///合约自动支付时间
			from : msg.sender,
			to : _seller,
			isOvered : false
		}));
		traders[msg.sender].isPermitted = false;
		emit Purchased(msg.sender,_seller,_value,(now + _lastTime));
    }
```

 3. 应收账款的转让上链：指定需要转移账单的核心企业rootCompany，查找Bills中是否存在此人的该账单，如果存在转让value的账额，并修改原账单的欠款金额，新账单的归还时间为旧的截止时间。转移账单even被记录。
```
function transferBill(address _rootCompany, address _receiver, uint _value)public//转移账单
    {
        traders[msg.sender].isPermitted = true;
		require(
			traders[msg.sender].isPermitted == true,
			"Only getting right from bank can trade"
		);
        require(_receiver != msg.sender, "Self-trader is disallowed.");
		require(_receiver != address(0),"Not valid address");
		require(_value > 0, "The value need to bigger than zero");
        
        bool isSuccess = false;
		//查找区块中是否存在来自rootCompany 欠 msg.sender的账单
		
		for(uint i = 0; i < Bills.length; i++){
			if(Bills[i].from == _rootCompany && Bills[i].to == msg.sender && Bills[i].isOvered == false){
				if(Bills[i].value >= _value){
    				Bills.push(Bill({
    					value : _value,
    					billEndTime : Bills[i].billEndTime,
    					from : _rootCompany,
    					to : _receiver,
    					isOvered : false
    				}));
    				Bills[i].value -= _value;
    				isSuccess = true;
    				traders[msg.sender].isPermitted = false;
    				emit BillTransformed(_rootCompany,msg.sender,_receiver,_value);
    				break;
				}
		    }
		}
		require(isSuccess == true,"transfering Bill failed");
    }
```
 4. 利用应收账款向银行融资上链：通过查找融资方拥有的所有被偿还账单的总偿还金额是否大于融资的金额，如果满足条件，银行向融资方借款，并签订融资应偿还的账单。融资event被保存。
```
function applyForFunde(uint _value,uint _lastTime)public//向银行申请融资,不能大于它持有的账单金额综合
    {
		require(bank != msg.sender, "Self-funde is disallowed.");
		require(_value > 0, "The value need to bigger than zero");
		require(_lastTime > 0, "The lastTime need to bigger than zero");
        bool isSuccess = false;
		uint totalValue = 0;
		for(uint i = 0; i < Bills.length; i++){
			if(Bills[i].to == msg.sender && Bills[i].isOvered == false){
				totalValue += Bills[i].value;
				if(totalValue >= _value){
					Bills.push(Bill({
    					value : _value,
    					billEndTime : now + _lastTime,//合约自动支付时间
    					from : msg.sender,
    					to : bank,
    					isOvered : false
				    }));
    				transferFrom(bank,msg.sender,_value);
    				isSuccess = true;
    				emit FundeApplied(_value,(now+_lastTime));
    				break;
				}
			}
		}
		require(isSuccess == true,"applying for funde failed");
    }
```
5.  应收账款支付结算上链：启动账单自动偿还功能，超过截止日期的账单自动根据信息进行账户转账，并将账单改为结束状态。转账event被记录。
```
function autoPayForBill()public//支付应付账单
    {
        for(uint8 i = 0; i < Bills.length; i++){
			//条件
		    require(now >= Bills[i].billEndTime && Bills[i].isOvered == false);
            require(Bills[i].value >= 0,"This Bills %d's value is not valid");
            //影响
            Bills[i].isOvered = true;
			//转账
			transferFrom(Bills[i].from,Bills[i].to,Bills[i].value);
        }
    }
}
function transferFrom(address from, address to, uint amount) private returns (bool success) {
        move(from, to, amount);
        emit Transfer(from, to, amount);
        return true;
    }
```

## 三、	功能测试
实验截图
* 部署：
![部署](https://img-blog.csdnimg.cn/20191214112831389.png)
* 获取银行address:
![获取银行address](https://img-blog.csdnimg.cn/20191214112855786.png)
* 获取accounts[1]和accounts[2]
![获取账户](https://img-blog.csdnimg.cn/20191214112915411.png)
* 获取账户余额:
 ![在这里插入图获取账户余额描述](https://img-blog.csdnimg.cn/20191214112928644.png)
* 获取账单个数：
![获取账单个数](https://img-blog.csdnimg.cn/20191214113422615.png)
* 获取账单信息：
![获取账单信息](https://img-blog.csdnimg.cn/20191214113455794.png)
* 购买:
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20191214112941917.png)
* 转移账单：
 ![转移账单](https://img-blog.csdnimg.cn/20191214112947500.png)
* 融资：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20191214113003418.png)
* 自动支付：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20191214113012122.png)
## 四、	界面展示
![运行界面](https://img-blog.csdnimg.cn/20191214113035365.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1poYW9teTg=,size_16,color_FFFFFF,t_70)
方案使用javasdk和web3sdk来实现服务端和链端的交互，从而构建区块链应用
![sdk框架](https://img-blog.csdnimg.cn/20191213220054104.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1poYW9teTg=,size_16,color_FFFFFF,t_70)
接口设计：
![接口](https://img-blog.csdnimg.cn/20191214113103520.png)
## 五、	心得体会
本次实验有点困难，前期solidity进行合约的编写在语言学习上刚开始有些不适应，在学习了相关的一些示例后有了些掌握，后面写起来比较轻松。刚开始使用的Bill是保存在公司内部信息中的，后面觉得每笔交易要保存两次，比较麻烦没有再这样做，改为了在合约中存储。
	后面写接口和前段时，原来是打算用html和node.js来写的，html比较简单，但是node.js学的不太熟就没有用，最后采用了技术文档中的javasdk来创建应用，调用功能时和在fisco-bcos中调用合约的情况比较符合，前端界面比较简陋，主要是时间不太够了，之后会考虑用java库来编写一个界面。
	总之，这次通过实验，更进一步了解了fisco-bcos的优异环境和使用方式，对构建区块链应用有了一定的收获。


