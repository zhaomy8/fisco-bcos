pragma solidity >=0.4.22 <0.7.0;

contract Bill{
    struct Bill{
        uint value;
		uint billEndTime;//ºÏÔŒ×Ô¶¯Ö§ž¶Ê±Œä
		address from;
        address to;
		bool isOvered;//ÊÇ·ñÖÕÖ¹
    }

	struct Trader{
		bool isPermitted;//ÔÚœ»Ò×Õß·¢Éú¹ºÂò»ò×ªÈÃ¶©µ¥Ç°±ØÐëÏÈÓÉÒøÐÐœøÐÐÊÚÈš
	}
	
//µÚÈý·œÈÏÖ€œðÈÚ»ú¹¹
	address public bank;
	address [] public accounts; 
	mapping(address => uint256) balances;
	mapping(address => Trader) private traders;
	Bill [] public Bills;//ºÏÔŒÖÐµÄÕËµ¥
	
	
	function move(address from, address to, uint amount) internal {
        require(balances[from] >= amount);
        require(balances[to] + amount >= balances[to]);
        balances[from] -= amount;
        balances[to] += amount;
    }
	function getBalanceOf(address tokenOwner) public view returns (uint balance) {
        return balances[tokenOwner];
    }
    function transfer(address to, uint amount) private returns (bool success) {
        move(msg.sender, to, amount);
        emit Transfer(msg.sender, to, amount);
        return true;
    }
	function transferFrom(address from, address to, uint amount) private returns (bool success) {
        move(from, to, amount);
        emit Transfer(from, to, amount);
        return true;
    }

	event Purchased(address purchaser, address seller, uint value, uint billEndTime);
	event BillTransformed(address rootCompany, address purchaser, address seller, uint value);
	event FundeApplied (uint value,uint billEndTime);
	event Transfer(address from, address to, uint amount);

	
    constructor() public{
        bank = msg.sender;
        balances[bank] = 100000000;
        accounts.push(bank);
        accounts.push(0xCA35b7d915458EF540aDe6068dFe2F44E8fa733c);
        accounts.push(0x14723A09ACff6D2A60DcdF7aA4AFf308FDDC160C);
        accounts.push(0x4B0897b0513fdC7C541B6d9D7E929C4e5364D2dB);
        //ÔöŒÓÕËµ¥×÷Îª²âÊÔÊ¹ÓÃ 
        Bills.push(Bill({
            value : 10000,
			billEndTime : 100000,
			from : accounts[1],
			to : bank,
			isOvered : false
        }));
        Bills.push(Bill({
            value : 20000,
			billEndTime : 100000,
			from : accounts[1],
			to : accounts[2],
			isOvered : false
        }));
        for(uint i = 1; i < 4; i++){
            balances[accounts[i]] = 50000;
        }
    }
    
    function showAccount(uint i) public view returns(address account){
        return accounts[i];
    }
    
    function init() public{
        balances[msg.sender] = 50000;
    }
    
    function getBillNum() public view returns (uint num){
        return Bills.length;
    }
	
    
    
	//ÕËµ¥µÄÇ©¶©»ò×ªÒÆÐèÒªÒøÐÐžø³öÈšÏÞ
	function giveRightToTrader(address trader) private{
		require (msg.sender == bank,
			"Only bank can give right to trade"
		);
		require (traders[trader].isPermitted == false);
		traders[trader].isPermitted = true;
	}

    function purchaseAndMakeBill(address _seller,uint _value,uint _lastTime)public//¹ºÂòÎïÆ·£¬Ç©¶©ÕËµ¥
    {
        traders[msg.sender].isPermitted = true;
		require(
			traders[msg.sender].isPermitted == true,
			"Only getting right from bank can trade"
		);
        require(_seller != msg.sender, "Self-trader is disallowed.");
		require(_seller != address(0) && _value >= 0,"Not valid address or value");
		require(_lastTime > 0, "The lastTime need to bigger than zero");
       
		//Ç©¶©ÕËµ¥
        Bills.push(Bill({
			value : _value,
			billEndTime : (now + _lastTime),///ºÏÔŒ×Ô¶¯Ö§ž¶Ê±Œä
			from : msg.sender,
			to : _seller,
			isOvered : false
		}));
		traders[msg.sender].isPermitted = false;
		emit Purchased(msg.sender,_seller,_value,(now + _lastTime));
    }
    
    function transferBill(address _rootCompany, address _receiver, uint _value)public//×ªÒÆÕËµ¥
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
		//²éÕÒÇø¿éÖÐÊÇ·ñŽæÔÚÀŽ×ÔrootCompany Ç· msg.senderµÄÕËµ¥
		
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
    
    function applyForFunde(uint _value,uint _lastTime)public//ÏòÒøÐÐÉêÇëÈÚ×Ê,²»ÄÜŽóÓÚËü³ÖÓÐµÄÕËµ¥œð¶î×ÛºÏ
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
    					billEndTime : now + _lastTime,//ºÏÔŒ×Ô¶¯Ö§ž¶Ê±Œä
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
    
    function autoPayForBill()public//Ö§ž¶ÓŠž¶ÕËµ¥
    {
        for(uint8 i = 0; i < Bills.length; i++){
			//ÌõŒþ
		    require(now >= Bills[i].billEndTime && Bills[i].isOvered == false);
            require(Bills[i].value >= 0,"This Bills %d's value is not valid");
            //Ó°Ïì
            Bills[i].isOvered = true;
			//×ªÕË
			transferFrom(Bills[i].from,Bills[i].to,Bills[i].value);
        }
    }
}
