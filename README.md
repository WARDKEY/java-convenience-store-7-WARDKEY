# java-convenience-store-precourse

## 미션 4주차 - 편의점

**구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템을 구현한다.**

## 기능 요구사항
1. 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.
    - 총 구매액은 상품별 가격과 수량을 곱하여 계산하며, 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출한다.


2. 구매 내역과 산출한 금액 정보를 영수증으로 출력한다.


3. 영수증 출력 후 추가 구매를 진행할지 또는 종료할지를 선택할 수 있다.


4. 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
   - Exception이 아닌 IllegalArgumentException, IllegalStateException 등과 같은 명확한 유형을 처리한다.
     -  구매할 상품과 수량 형식이 올바르지 않은 경우: [ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.
     - 존재하지 않는 상품을 입력한 경우: [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.
     - 구매 수량이 재고 수량을 초과한 경우: [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.
     - 기타 잘못된 입력의 경우: [ERROR] 잘못된 입력입니다. 다시 입력해 주세요.


5. 재고 관리
   - 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
   - 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
   - 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.


6. 프로모션 할인
   - 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
   - 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.
   - 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.
   - 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
     - 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
   - 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
   - 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.


7. 멤버십 할인
    - 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
    - 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
    - 멤버십 할인의 최대 한도는 8,000원이다.


8. 영수증 출력
    - 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.
    - 영수증 항목은 아래와 같다.
      - 구매 상품 내역 : 구매한 상품명, 수량, 가격
      - 행사할인 : 프로모션에 의해 추가로 할인된 금액
      - 멤버십 할인 : 멤버심에 의해 추가로 할인된 금액
      - 내실돈 : 최종 결제 금액
    - 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 한다.


---
## 기능 목록
- **입출력**
  1. 환영인사, 상품명, 가격, 프로모션 이름, 재고 안내 메시지 출력 기능
     - 만약 재고가 0개일 경우 "재고 없음"을 출력한다.
  2. 구매할 상품명, 수량 입력 기능
     - 상품은 "[]"로 구분하고 수량은 "-"로 구분하며 여러 개의 수량을 입력할 경우 ","로 구분하여 입력한다.
  3. 프로모션 할인 혜택 안내 매시지 출력 기능
     - "현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)" 라는 문장을 출력한다.
  4. 일부 수량 정가 결제 안내 메시지 출력 기능
     - "현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)" 라는 문장을 출력한다.
  5. 멤버십 할인 여부 안내 메시지 출력 기능
     - "멤버십 할인을 받으시겠습니까? (Y/N)" 라는 문장을 출력한다.
  6. 멤버십 할인 여부 입력 기능
     - Y또는 N을 입력한다.
  7. 영수증 출력 기능
     - 영수증 내역을 출력한다.
  8. 추가 구매 안내 메시지 출력 기능 
     - "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)" 라는 메시지를 출력한다.


- **파일**
   1. 파일로부터 재고에 대한 내용을 불러와서 상품 객체에 저장한다.
   2. 파일로부터 프로모션에 대한 정보를 불러와서 프로모션 객체에 저장한다.


- **재고**
   1. 상품의 이름, 가격, 수량, 프로모션 정보를 저장한다. 
   2. 선택한 상품에 대한 재고를 차감한다.
   3. 상품의 수량이 0일 경우 "재고 없음"으로 저장한다.
   4. 고객의 입력한 상품이 재고에 존재하는지 판단한다.


- **상품**
   1. 각각의 상품에 대한 정보를 리스트로 저장한다.


- **프로모션**
   1. 날짜가 프로모션 기간 내에 포함된 경우 할인을 적용한다.
   2. 동일 상품에 여러 프로모션이 적용되지 않도록 한다.
   3. 프로덕션 재고여부를 확인하여 프로덕션 재고에만 프로덕션을 적용한다.
   4. 프로모션 재고가 부족할 경우 일반 재고를 사용한다.
   5. 고객이 프로모션 상품의 수량을 적게 가져왔는지 판단한다.
   6. 프로모션 재고가 부족한지 판단한다.


- **멤버십**
   1. 멤버십 회원 여부 정보를 저장한다.
   2. 멤버십 회원이라면 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
   3. 멤버십 할인의 최대 한도는 8,000원이다.


- **영수증**
   1. 총구매액, 행사할인, 멤버십할인, 내실돈 정보를 저장한다.
   2. 총구매액을 계산한다.
   3. 행사할인을 계산한다.
   4. 멤버십할인을 계산한다.
   5. 내실돈을 계산한다.


---
## 예외 처리
- 고객 구매 입력
  - 고객 구매 입력 시 개별 상품이 "\["로 시작하거나 "\]"로 끝나지 않는 경우
    - IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다.");를 터트리고 입력을 다시 받는다.
  - 고객 구매 입력 시 "-"를 입력하지 않은 경우
    - IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다.");를 터트리고 입력을 다시 받는다.
  - 고객 구매 입력 시 ","로 끝나는 경우
    - IllegalArgumentException("[ERROR] 상품을 이어서 입력해주세요.");를 터트리고 입력을 다시 받는다.
  - 고객 구매 입력 시 상품명이 비었을 경우
    - IllegalArgumentException("[ERROR] 상품을 입력해주세요.");를 터트리고 입력을 다시 받는다.
  - 고객 구매 입력 시 수량이 비었을 경우
    - IllegalArgumentException("[ERROR] 수량을 입력해주세요.");를 터트리고 입력을 다시 받는다.
  - 고객 구매 입력 시 재고에 없는 상품이거나 재고 수량이 0인 경우
    - IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");를 터트리고 입력을 다시 받는다.
  - 고객 구매 입력 시 재고 수량보다 많은 경우
    - IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");를 터트리고 고객입력을 다시 받는다. 
  - 고객 구매 입력 시 상품 개수를 숫자로 입력하지 않은 경우
    - NumberFormatException("[ERROR] 상품 개수는 숫자를 입력해주세요.");를 터트리고 입력을 다시 받는다.


- 프로모션 수량 추가 여부 입력
  - 프로모션 수량 추가 여부 입력 시 "Y"또는 "N"이 아닌 다른 값을 입력한 경우
    - IllegalArgumentException("[ERROR] 프로모션 추가 여부는 "Y"또는 "N"을 입력해주세요.");를 터트리고 입력을 다시 받는다.
  - 프로모션 수량 추가 여부 입력 시 공백을 입력한 경우
    - IllegalArgumentException("[ERROR] 프로모션 추가 여부를 입력해주세요.");를 터트리고 입력을 다시 받는다.


- 일부 수량 결제 여부 입력
  - 일부 수량 결제 여부 입력 시 "Y"또는 "N"이 아닌 다른 값을 입력한 경우
    - IllegalArgumentException("[ERROR] 일부 수량 결제 여부는 "Y"또는 "N"을 입력해주세요.");를 터트리고 입력을 다시 받는다.
  - 일부 수량 결제 여부 입력 시 공백을 입력한 경우
    - IllegalArgumentException("[ERROR] 일부 수량 결제 여부를 입력해주세요.");를 터트리고 입력을 다시 받는다.


- 멤버십 할인 여부 입력
  - 멤버십 할인 여부 입력 시 "Y"또는 "N"이 아닌 다른 값을 입력한 경우
    - IllegalArgumentException("[ERROR] 멤버십 할인 여부는 "Y"또는 "N"을 입력해주세요.");를 터트리고 입력을 다시 받는다.
  - 멤버십 할인 여부 입력 시 공백을 입력한 경우
    - IllegalArgumentException("[ERROR] 멤버십 할인 여부를 입력해주세요.");를 터트리고 입력을 다시 받는다.


- 추가 구매 여부 입력
  - 추가 구매 여부 입력 시 "Y"또는 "N"이 아닌 다른 값을 입력한 경우
    - IllegalArgumentException("[ERROR] 추가 구매 여부는 "Y"또는 "N"을 입력해주세요.");를 터트리고 입력을 다시 받는다.
  - 추가 구매 여부 입력 시 공백을 입력한 경우
    - IllegalArgumentException("[ERROR] 추가 구매 여부를 입력해주세요.");를 터트리고 입력을 다시 받는다.


---
## 사용 예제
**올바른 입력인 경우**
```
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 재고 없음 탄산2+1
- 콜라 1,000원 7개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 재고 없음
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[오렌지주스-1]

현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
Y

멤버십 할인을 받으시겠습니까? (Y/N)
Y

==============W 편의점================
상품명		수량	금액
오렌지주스		2 	3,600
=============증	정===============
오렌지주스		1
====================================
총구매액		2	3,600
행사할인			-1,800
멤버십할인			-0
내실돈			 1,800

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
N
```


**잘못된 입력인 경우**
```
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 재고 없음 탄산2+1
- 콜라 1,000원 7개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 재고 없음
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[오렌지주스-1]

현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
Y

멤버십 할인을 받으시겠습니까? (Y/N)
A
[ERROR] 멤버십 할인 여부는 "Y"또는 "N"을 입력해주세요.
Y

==============W 편의점================
상품명		수량	금액
오렌지주스		2 	3,600
=============증	정===============
오렌지주스		1
====================================
총구매액		2	3,600
행사할인			-1,800
멤버십할인			-0
내실돈			 1,800

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
N
```