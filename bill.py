#!/usr/bin/python
# -*- coding: UTF-8 -*-

from email.mime.text import MIMEText
import smtplib
import time
import datetime
import mysql.connector
from apscheduler.schedulers.blocking import BlockingScheduler

class Bill(object):

    def sendEmail(self, content):

        #发件人的用户名
        _user = "发件人邮箱"
        #发件人的密码，如果是qq邮箱，那么请换成口令，并开启服务
        _pwd  = "输入密码"
        #收件人邮箱
         _to   = ["收件人邮箱", "收件人邮箱"]

        msg = MIMEText(content, 'plain', 'UTF-8')
        msg["Subject"] = u"关于线下理财加币事项"
        msg["From"]    = _user
        msg["To"]      = ','.join(_to)
        # msg["CC"]      = _to

        try:
            # SSL方式连接，端口号：465 很重要
            # 腾讯企业邮箱 smtp.exmail.qq.com，注意这里的替换
            send = smtplib.SMTP_SSL("smtp.exmail.qq.com", 465)
            send.login(_user, _pwd)
            send.sendmail(_user, _to, msg.as_string())
            send.quit()
            print "Send success!"
        except smtplib.SMTPException,e:
            print "Falied,%s"%e

    def select(self):
        conn = mysql.connector.connect(user='root', password='root', database='bill', use_unicode=True)
        cursor = conn.cursor()
        now = self.timeHandler()
        cursor.execute('select * from bill_info where end_time = %s', (now, ))
        values = cursor.fetchall()
        cursor.close()
        self.dataHandler(values)

    # get yesterday time
    def timeHandler(self):
        now_date = datetime.datetime.now()
        yes_date = now_date + datetime.timedelta(days=3)
        yes_str = yes_date.strftime("%Y-%m-%d")
        # t = time.strftime('%Y-%m-%d',time.localtime(time.time()))
        # strftime means str for time
        # strptime means str parse time
        timeStamp = time.mktime(time.strptime(yes_str, '%Y-%m-%d'))
        return str(int(timeStamp * 1000))

    def dataHandler(self, values):
        content = ""
        #金卉线下委托理财10万元，年化收益12%，投资起算日2616/6/27，到期日2016/7/27，投资天数30天，到期利息100000*12%/365*30=986.30元。
        for value in values:
            num = value[4]
            startTime = time.strftime("%Y-%m-%d", time.localtime(int(num) / 1000))
            money = str(value[2])
            rate = str(value[3] * 100)
            days = str(value[6])
            interest = str(value[7])
            formula = money + "*" + rate + "%*" + days + "/365" + "=" + interest + "元"
            s = str(value[1]) + "线下委托理财" + money + "万元， 年化收益" + rate + "%, 投资起算日" + startTime + ", 投资天数" + days + "天，到期利息" + formula + "."
            content = content + s + "\n"
        self.sendEmail(content)

def send():
    bill = Bill()
    bill.select()

# 定时器
sched = BlockingScheduler()
sched.add_job(send, 'cron', hour=0, minute=0, second=1)
sched.start()
