#!/bin/bash 

function usage() 
{
    echo " Usage : "
    echo "   bash bill_run.sh deploy"
    echo "   bash bill_run.sh init"
    echo "   bash bill_run.sh getBalanceOf account"
    echo "   bash bill_run.sh showAccount i"
    echo "   bash bill_run.sh bank"
    echo "   bash bill_run.sh getBillNum"
    echo "   bash bill_run.sh autoPayBill"
    echo "   bash bill_run.sh purchaseAndMakeBill sellerAddr value lastingTime"
    echo "   bash bill_run.sh transferBill rootCompany seller value"
    echo "   bash bill_run.sh applyForFunde value lastingTime"
    echo " "
    echo " "
    echo "examples : "
    echo "   bash bill_run.sh deploy "
    echo "   bash bill_run.sh init"
    echo "   bash bill_run.sh getBalanceOf 0xca35b7d915458ef540ade6068dfe2f44e8fa733c"
    echo "   bash bill_run.sh showAccount 1"
    echo "   bash bill_run.sh bank"
    echo "   bash bill_run.sh getBillNum"
    echo "   bash bill_run.sh autoPayBill"
    echo "   bash bill_run.sh purchaseAndMakeBill 0xca35b7d915458ef540ade6068dfe2f44e8fa733c 10000 60"
    echo "   bash bill_run.sh transferBill 0xca35b7d915458ef540ade6068dfe2f44e8fa733c 0x14723a09acff6d2a60dcdf7aa4aff308fddc160c 5000"
    echo "   bash bill_run.sh applyForFunde 4000 50"
    exit 0
}

    case $1 in
    deploy)
            [ $# -lt 1 ] && { usage; }
            ;;
    init)
            [ $# -lt 1 ] && { usage; }
            ;;
    getBalanceOf)
            [ $# -lt 2 ] && { usage; }
            ;;
    showAccount)
            [ $# -lt 2 ] && { usage; }
            ;;
    bank)
            [ $# -lt 1 ] && { usage; }
            ;;
    getBillNum)
            [ $# -lt 1 ] && { usage; }
            ;;
    Bills)
            [ $# -lt 2 ] && { usage; }
            ;;
    
    autoPayBill)
            [ $# -lt 1 ] && { usage; }
            ;;
    purchaseAndMakeBill)
            [ $# -lt 4 ] && { usage; }
            ;;
    transferBill)
            [ $# -lt 4 ] && { usage; }
            ;;
    applyForFunde)
            [ $# -lt 3 ] && { usage; }
            ;;
    *)
        usage
            ;;
    esac

    java -Djdk.tls.namedGroups="secp256k1" -cp 'apps/*:conf/:lib/*' org.fisco.bcos.asset.client.BillClient $@

