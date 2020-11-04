<div class="modal fade" id="addSupplierModal">
	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<h1 class="modal-title">Add Supplier</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="AddSupplier.do" autocomplete="off">
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label">Name:</label>
						<input type="text" name="name" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Address:</label>
						<input type="text" name="address" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">City:</label>
						<input type="text" name="city" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Region:</label>
						<input type="text" name="region" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Postal Code:</label>
						<input type="number" name="postalCode" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Contact Number:</label>
						<input type="number" name="contactNumber" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Fax Number:</label>
						<input type="number" name="faxNumber" class="form-control" required/>
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