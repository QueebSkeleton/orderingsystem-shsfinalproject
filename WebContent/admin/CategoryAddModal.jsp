<div class="modal fade" id="addCategoryModal">
	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<h1 class="modal-title">Add Category</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="AddCategory.do" autocomplete="off">
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label">Name:</label>
						<input type="text" name="name" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Description:</label>
						<textarea name="description" class="form-control"></textarea>
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