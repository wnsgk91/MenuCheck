import requests
from bs4 import BeautifulSoup
import urllib

import json 
from collections import OrderedDict

import pymysql




def print_detail():

	file_data = OrderedDict()
	file_name = "food_kor_name"

	for i in range(51665,51667):
		res = requests.get('http://www.hansik.or.kr/kr/board/dn/view/017?seq='+str(i)+'&bbsId=017&curPage=1&bbsType=dn&menuId=56&korFirst=&engFirst=&searchWord=&rowSize=10')
	#	res = requests.get('http://www.hansik.or.kr/kr/board/dn/view/017?seq=51043&bbsId=017&curPage=70&bbsType=dn&menuId=56&korFirst=&engFirst=&searchWord=&rowSize=10')
		soup = BeautifulSoup(res.content, 'html.parser')
		name = soup.select('.data')
		detail = soup.select('.pre-contents')

		file_data["name"] = name[0].text
		#file_data["category"] = name[1].text
		#file_data["detail"] = detail[0].text

		with open("/Users/junha_lee/Desktop/{}.json".format(file_name),'a+',encoding="utf-8") as make_file:
			json.dump(file_data, make_file, ensure_ascii=False, indent=2)


def find_img():

	conn = pymysql.connect(host='localhost', user='root', password='dlwnsgk94', charset='utf8', database='and_')
	curs = conn.cursor()

	sql = "SELECT name FROM food_kor"
	curs.execute(sql)

	rows = curs.fetchall()
	name = list(rows)

	file_data = OrderedDict()
	file_name = "src"



	for i in range(0,92):
		res = requests.get('https://www.google.co.kr/search?q='+str(name[i])+'&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiAqreq-c7eAhVMa94KHUh0APoQ_AUIDigB&biw=1680&bih=971&dpr=2#imgrc=pI3MO8E28BJYkM:')
		soup = BeautifulSoup(res.content, 'html.parser')
		img = soup.select('img')
		src = img[1].get('src')

		file_data["name"] = name[i]
		file_data["src"] = src

		with open("/Users/junha_lee/Desktop/src.json",'a+',encoding="utf-8") as make_file:
			json.dump(file_data, make_file, ensure_ascii=False, indent=2)



def main():
	print_detail()
	#find_img()

if __name__ == '__main__':
	main()
