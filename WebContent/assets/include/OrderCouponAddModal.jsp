<div class="modal fade" id="addOrderCouponModal">
	<div class="modal-dialog">
		<div class="modal-content">
			
			<div class="modal-header">
				<h1 class="modal-title">Add Coupon</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="AddOrderCoupon.do" autocomplete="off">
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label">Code:</label>
						<input type="text" name="code" class="form-control" required/>	
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Add</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>
			</form>
			
		</div>
	</div>
</div>