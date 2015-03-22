import groovy.json.JsonBuilder
import infrastructure.GitHub
import service.GitHubRepositoryService
import util.CrossOriginPolicy

import static com.google.appengine.api.utils.SystemProperty.Environment.Value.Development

CrossOriginPolicy.allowOrigin(response, headers)

assert params.fullName
assert app.env.name == Development || headers.Authorization
assert app.env.name == Development || headers.Authorization.startsWith('token ')

final gitHub = headers.Authorization ? new GitHub(Authorization: headers.Authorization) : new GitHub()

final service = new GitHubRepositoryService(gitHub)
final metadata = service.queryMetadata(params.fullName)

response.contentType = 'application/json'
println new JsonBuilder(metadata)
