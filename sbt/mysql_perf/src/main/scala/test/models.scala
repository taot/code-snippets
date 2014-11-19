package test

case class Transaction(
  accountId: Long,
  securityId: Long,
  quantity: BigDecimal,
  amount: BigDecimal
)

case class Position(
  id: Long,
  accountId: Long,
  securityId: Long,
  quantity: BigDecimal,
  carryingValue: BigDecimal
)